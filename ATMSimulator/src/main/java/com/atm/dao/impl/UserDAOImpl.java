package com.atm.dao.impl;

import com.atm.dao.UserDAO;
import com.atm.model.User;
import com.atm.util.DatabaseUtil;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserByCard(String cardNumber) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE card_number = ?");
            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("card_number"),
                    rs.getString("pin"),
                    rs.getString("mobile"),
                    rs.getString("account_type"),
                    rs.getString("status")
                );
                user.setAccountNumber(rs.getString("account_number"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by card: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByAccount(String accountNumber) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE account_number = ?");
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("card_number"),
                    rs.getString("pin"),
                    rs.getString("mobile"),
                    rs.getString("account_type"),
                    rs.getString("status")
                );
                user.setAccountNumber(rs.getString("account_number"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by account: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateUserPin(String cardNumber, String newPin) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET pin = ? WHERE card_number = ?");
            stmt.setString(1, newPin);
            stmt.setString(2, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user pin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUserStatus(String cardNumber, String status) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET status = ? WHERE card_number = ?");
            stmt.setString(1, status);
            stmt.setString(2, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("card_number"),
                    rs.getString("pin"),
                    rs.getString("mobile"),
                    rs.getString("account_type"),
                    rs.getString("status")
                );
                user.setAccountNumber(rs.getString("account_number"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean createUser(User user) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (user_id, name, card_number, pin, mobile, account_type, status, account_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getCardNumber());
            stmt.setString(4, user.getPin());
            stmt.setString(5, user.getMobile());
            stmt.setString(6, user.getAccountType());
            stmt.setString(7, user.getStatus());
            stmt.setString(8, user.getAccountNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
}