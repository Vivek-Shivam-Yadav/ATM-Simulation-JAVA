package com.atm.dao.impl;

import com.atm.dao.AdminDAO;
import com.atm.model.Admin;
import com.atm.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Admin getAdminByUsername(String username) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM admin WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                if (rs.getTimestamp("last_login") != null) {
                    admin.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());
                }
                return admin;
            }
        } catch (SQLException e) {
            System.err.println("Error getting admin by username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean validateAdmin(String username, String password) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM admin WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Update last login time
                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE admin SET last_login = NOW() WHERE username = ?");
                updateStmt.setString(1, username);
                updateStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error validating admin: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateAdminPassword(String username, String newPassword) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE admin SET password = ? WHERE username = ?");
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating admin password: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createAdmin(Admin admin) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO admin (admin_id, username, password, role, last_login) VALUES (?, ?, ?, ?, NOW())");
            stmt.setInt(1, admin.getAdminId());
            stmt.setString(2, admin.getUsername());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating admin: " + e.getMessage());
            return false;
        }
    }
}