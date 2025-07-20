package com.atm.dao.impl;

import com.atm.dao.SessionLogDAO;
import com.atm.model.SessionLog;
import com.atm.util.DatabaseUtil;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionLogDAOImpl implements SessionLogDAO {

    @Override
    public boolean createSession(SessionLog session) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO session_logs (session_id, account_number, login_time, status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, session.getSessionId());
            stmt.setString(2, session.getAccountNumber());
            stmt.setTimestamp(3, Timestamp.valueOf(session.getLoginTime()));
            stmt.setString(4, session.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating session: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSession(String sessionId, String status) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt;
            if ("logged_out".equals(status)) {
                stmt = conn.prepareStatement(
                    "UPDATE session_logs SET status = ?, logout_time = NOW() WHERE session_id = ?");
            } else {
                stmt = conn.prepareStatement(
                    "UPDATE session_logs SET status = ? WHERE session_id = ?");
            }
            stmt.setString(1, status);
            stmt.setString(2, sessionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating session: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<SessionLog> getSessionsByAccount(String accountNumber) {
        List<SessionLog> sessions = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM session_logs WHERE account_number = ? ORDER BY login_time DESC");
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                SessionLog session = new SessionLog(
                    rs.getString("session_id"),
                    rs.getString("account_number"),
                    rs.getTimestamp("login_time").toLocalDateTime(),
                    rs.getString("status")
                );
                if (rs.getTimestamp("logout_time") != null) {
                    session.setLogoutTime(rs.getTimestamp("logout_time").toLocalDateTime());
                }
                sessions.add(session);
            }
        } catch (SQLException e) {
            System.err.println("Error getting sessions by account: " + e.getMessage());
        }
        return sessions;
    }

    @Override
    public List<SessionLog> getAllActiveSessions() {
        List<SessionLog> sessions = new ArrayList<>();
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM session_logs WHERE status = 'active' ORDER BY login_time DESC");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                SessionLog session = new SessionLog(
                    rs.getString("session_id"),
                    rs.getString("account_number"),
                    rs.getTimestamp("login_time").toLocalDateTime(),
                    rs.getString("status")
                );
                if (rs.getTimestamp("logout_time") != null) {
                    session.setLogoutTime(rs.getTimestamp("logout_time").toLocalDateTime());
                }
                sessions.add(session);
            }
        } catch (SQLException e) {
            System.err.println("Error getting active sessions: " + e.getMessage());
        }
        return sessions;
    }

    @Override
    public SessionLog getSessionById(String sessionId) {
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM session_logs WHERE session_id = ?");
            stmt.setString(1, sessionId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                SessionLog session = new SessionLog(
                    rs.getString("session_id"),
                    rs.getString("account_number"),
                    rs.getTimestamp("login_time").toLocalDateTime(),
                    rs.getString("status")
                );
                if (rs.getTimestamp("logout_time") != null) {
                    session.setLogoutTime(rs.getTimestamp("logout_time").toLocalDateTime());
                }
                return session;
            }
        } catch (SQLException e) {
            System.err.println("Error getting session by id: " + e.getMessage());
        }
        return null;
    }
}