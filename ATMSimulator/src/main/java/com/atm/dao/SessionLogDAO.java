package com.atm.dao;

import com.atm.model.SessionLog;
import java.util.List;

public interface SessionLogDAO {
    boolean createSession(SessionLog session);
    boolean updateSession(String sessionId, String status);
    List<SessionLog> getSessionsByAccount(String accountNumber);
    List<SessionLog> getAllActiveSessions();
    SessionLog getSessionById(String sessionId);
}