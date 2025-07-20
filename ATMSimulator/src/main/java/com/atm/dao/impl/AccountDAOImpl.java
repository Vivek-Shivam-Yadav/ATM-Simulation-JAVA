package com.atm.dao.impl;

import com.atm.dao.AccountDAO;
import com.atm.model.Account;
import com.atm.util.DatabaseUtil;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account getAccountByNumber(String accountNumber) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM accounts WHERE account_number = ?");
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Account account = new Account(
                    rs.getString("account_number"),
                    rs.getInt("user_id"),
                    rs.getDouble("balance")
                );
                if (rs.getTimestamp("last_transaction_at") != null) {
                    account.setLastTransactionAt(rs.getTimestamp("last_transaction_at").toLocalDateTime());
                }
                return account;
            }
        } catch (SQLException e) {
            System.err.println("Error getting account: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateAccountBalance(String accountNumber, double newBalance) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE accounts SET balance = ?, last_transaction_at = NOW() WHERE account_number = ?");
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating account balance: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createAccount(Account account) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO accounts (account_number, user_id, balance) VALUES (?, ?, ?)");
            stmt.setString(1, account.getAccountNumber());
            stmt.setInt(2, account.getUserId());
            stmt.setDouble(3, account.getBalance());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account(
                    rs.getString("account_number"),
                    rs.getInt("user_id"),
                    rs.getDouble("balance")
                );
                if (rs.getTimestamp("last_transaction_at") != null) {
                    account.setLastTransactionAt(rs.getTimestamp("last_transaction_at").toLocalDateTime());
                }
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all accounts: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public boolean deleteAccount(String accountNumber) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM accounts WHERE account_number = ?");
            stmt.setString(1, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
            return false;
        }
    }
}