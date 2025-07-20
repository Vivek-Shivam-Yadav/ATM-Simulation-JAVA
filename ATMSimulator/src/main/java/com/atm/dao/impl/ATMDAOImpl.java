package com.atm.dao.impl;

import com.atm.dao.ATMDAO;
import com.atm.util.DatabaseUtil;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMDAOImpl implements ATMDAO {

    @Override
    public boolean updateCashInventory(int denomination, int count) {
        if (count < 0) return false;
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO atm_cash (denomination, count) VALUES (?, ?) ON DUPLICATE KEY UPDATE count = ?");
            stmt.setInt(1, denomination);
            stmt.setInt(2, count);
            stmt.setInt(3, count);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating cash inventory: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Map<Integer, Integer> getCashInventory() {
        Map<Integer, Integer> inventory = new HashMap<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM atm_cash");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                inventory.put(rs.getInt("denomination"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting cash inventory: " + e.getMessage());
        }
        return inventory;
    }

    @Override
    public int getTotalCash() {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT SUM(denomination * count) as total FROM atm_cash");
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting total cash: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean addCash(int denomination, int count) {
        if (count <= 0) return false;
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO atm_cash (denomination, count) VALUES (?, ?) ON DUPLICATE KEY UPDATE count = count + ?");
            stmt.setInt(1, denomination);
            stmt.setInt(2, count);
            stmt.setInt(3, count);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding cash: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeCash(int denomination, int count) {
        if (count <= 0) return false;
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT count FROM atm_cash WHERE denomination = ?");
            checkStmt.setInt(1, denomination);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                int currentCount = rs.getInt("count");
                if (currentCount >= count) {
                    PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE atm_cash SET count = count - ? WHERE denomination = ?");
                    updateStmt.setInt(1, count);
                    updateStmt.setInt(2, denomination);
                    return updateStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error removing cash: " + e.getMessage());
        }
        return false;
    }
}