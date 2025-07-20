package com.atm.dao.impl;

import com.atm.dao.TransactionDAO;
import com.atm.model.Transaction;
import com.atm.util.DatabaseUtil;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public boolean createTransaction(Transaction transaction) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO transactions (transaction_id, account_number, type, amount, timestamp, target_account, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, transaction.getTransactionId());
            stmt.setString(2, transaction.getAccountNumber());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
            stmt.setString(6, transaction.getTargetAccount());
            stmt.setString(7, transaction.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating transaction: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC");
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getString("transaction_id"),
                    rs.getString("account_number"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("target_account"),
                    rs.getString("status")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error getting transactions by account: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public List<Transaction> getMiniStatement(String accountNumber, int limit) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC LIMIT ?");
            stmt.setString(1, accountNumber);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getString("transaction_id"),
                    rs.getString("account_number"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("target_account"),
                    rs.getString("status")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error getting mini statement: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(String transactionId) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM transactions WHERE transaction_id = ?");
            stmt.setString(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Transaction(
                    rs.getString("transaction_id"),
                    rs.getString("account_number"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("target_account"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM transactions ORDER BY timestamp DESC");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getString("transaction_id"),
                    rs.getString("account_number"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("target_account"),
                    rs.getString("status")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
        }
        return transactions;
    }
}