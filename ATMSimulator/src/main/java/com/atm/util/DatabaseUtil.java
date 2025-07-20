package com.atm.util;

import com.atm.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtil {

    private static Connection connection;

    public static void initializeDatabase() {
        try {
            Class.forName(DatabaseConfig.getDriverClass());
            connection = DriverManager.getConnection(
                DatabaseConfig.getConnectionUrl(),
                DatabaseConfig.getUsername(),
                DatabaseConfig.getPassword()
            );
            createTables();
            insertInitialData();
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    private static void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "card_number VARCHAR(16) UNIQUE, " +
                "pin VARCHAR(4), " +
                "mobile VARCHAR(10), " +
                "account_type VARCHAR(20), " +
                "status VARCHAR(20), " +
                "account_number VARCHAR(20) UNIQUE" +
                ")");

            // Accounts table
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                "account_number VARCHAR(20) PRIMARY KEY, " +
                "user_id INT, " +
                "balance DOUBLE, " +
                "last_transaction_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")");

            // Transactions table
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id VARCHAR(20) PRIMARY KEY, " +
                "account_number VARCHAR(20), " +
                "type VARCHAR(20), " +
                "amount DOUBLE, " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "target_account VARCHAR(20), " +
                "status VARCHAR(20)" +
                ")");

            // Admin table
            stmt.execute("CREATE TABLE IF NOT EXISTS admin (" +
                "admin_id INT PRIMARY KEY, " +
                "username VARCHAR(50) UNIQUE, " +
                "password VARCHAR(100), " +
                "role VARCHAR(20), " +
                "last_login TIMESTAMP" +
                ")");

            // ATM Cash table
            stmt.execute("CREATE TABLE IF NOT EXISTS atm_cash (" +
                "denomination INT PRIMARY KEY, " +
                "count INT DEFAULT 0" +
                ")");

            // Session logs table
            stmt.execute("CREATE TABLE IF NOT EXISTS session_logs (" +
                "session_id VARCHAR(20) PRIMARY KEY, " +
                "account_number VARCHAR(20), " +
                "login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "logout_time TIMESTAMP NULL, " +
                "status VARCHAR(20)" +
                ")");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    private static void insertInitialData() {
        try {
            // Check if data already exists
            PreparedStatement checkUsers = connection.prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet rs = checkUsers.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return; // Data already exists
            }

            // Insert sample users
            PreparedStatement insertUser = connection.prepareStatement(
                "INSERT INTO users (user_id, name, card_number, pin, mobile, account_type, status, account_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            
            insertUser.setInt(1, 1);
            insertUser.setString(2, "John Doe");
            insertUser.setString(3, "1234567890123456");
            insertUser.setString(4, "1234");
            insertUser.setString(5, "9876543210");
            insertUser.setString(6, "savings");
            insertUser.setString(7, "active");
            insertUser.setString(8, "ACC123");
            insertUser.executeUpdate();

            insertUser.setInt(1, 2);
            insertUser.setString(2, "Jane Smith");
            insertUser.setString(3, "9876543210987654");
            insertUser.setString(4, "5678");
            insertUser.setString(5, "9876543211");
            insertUser.setString(6, "current");
            insertUser.setString(7, "active");
            insertUser.setString(8, "ACC456");
            insertUser.executeUpdate();

            insertUser.setInt(1, 3);
            insertUser.setString(2, "Bob Johnson");
            insertUser.setString(3, "1111222233334444");
            insertUser.setString(4, "9999");
            insertUser.setString(5, "9876543212");
            insertUser.setString(6, "savings");
            insertUser.setString(7, "active");
            insertUser.setString(8, "ACC789");
            insertUser.executeUpdate();

            // Insert sample accounts
            PreparedStatement insertAccount = connection.prepareStatement(
                "INSERT INTO accounts (account_number, user_id, balance) VALUES (?, ?, ?)");
            
            insertAccount.setString(1, "ACC123");
            insertAccount.setInt(2, 1);
            insertAccount.setDouble(3, 5000.00);
            insertAccount.executeUpdate();

            insertAccount.setString(1, "ACC456");
            insertAccount.setInt(2, 2);
            insertAccount.setDouble(3, 3500.00);
            insertAccount.executeUpdate();

            insertAccount.setString(1, "ACC789");
            insertAccount.setInt(2, 3);
            insertAccount.setDouble(3, 2000.00);
            insertAccount.executeUpdate();

            // Insert admin users
            PreparedStatement insertAdmin = connection.prepareStatement(
                "INSERT INTO admin (admin_id, username, password, role, last_login) VALUES (?, ?, ?, ?, NOW())");
            
            insertAdmin.setInt(1, 1);
            insertAdmin.setString(2, "admin");
            insertAdmin.setString(3, "admin123");
            insertAdmin.setString(4, "SUPER_ADMIN");
            insertAdmin.executeUpdate();

            insertAdmin.setInt(1, 2);
            insertAdmin.setString(2, "manager");
            insertAdmin.setString(3, "manager123");
            insertAdmin.setString(4, "MANAGER");
            insertAdmin.executeUpdate();

            // Insert ATM cash denominations
            PreparedStatement insertCash = connection.prepareStatement(
                "INSERT INTO atm_cash (denomination, count) VALUES (?, ?)");
            
            insertCash.setInt(1, 20);
            insertCash.setInt(2, 100);
            insertCash.executeUpdate();

            insertCash.setInt(1, 50);
            insertCash.setInt(2, 50);
            insertCash.executeUpdate();

            insertCash.setInt(1, 100);
            insertCash.setInt(2, 30);
            insertCash.executeUpdate();

            insertCash.setInt(1, 500);
            insertCash.setInt(2, 20);
            insertCash.executeUpdate();

            insertCash.setInt(1, 1000);
            insertCash.setInt(2, 10);
            insertCash.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting initial data: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}