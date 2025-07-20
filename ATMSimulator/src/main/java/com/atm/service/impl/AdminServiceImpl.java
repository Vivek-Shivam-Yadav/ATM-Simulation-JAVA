package com.atm.service.impl;

import java.util.List;

import com.atm.dao.ATMDAO;
import com.atm.dao.AdminDAO;
import com.atm.dao.SessionLogDAO;
import com.atm.dao.impl.ATMDAOImpl;
import com.atm.dao.impl.AdminDAOImpl;
import com.atm.dao.impl.SessionLogDAOImpl;
import com.atm.model.SessionLog;
import com.atm.service.AdminService;

public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO = new AdminDAOImpl();
    private final ATMDAO atmDAO = new ATMDAOImpl();
    private final SessionLogDAO sessionDAO = new SessionLogDAOImpl();

    @Override
    public boolean validateAdmin(String username, String password) {
        return adminDAO.validateAdmin(username, password);
    }

    @Override
    public void refillCash(int denomination, int count) {
        atmDAO.addCash(denomination, count);
    }

    @Override
    public int checkATMCash() {
        return atmDAO.getTotalCash();
    }

    @Override
    public void viewUserLogs() {
        List<SessionLog> sessions = sessionDAO.getAllActiveSessions();
        if (sessions.isEmpty()) {
            System.out.println("No active user sessions found.");
        } else {
            System.out.println("Active User Sessions:");
            System.out.println("Session ID    | Account    | Login Time");
            System.out.println("----------------------------------------");
            sessions.forEach(session -> 
                System.out.printf("%-12s | %-10s | %s%n",
                    session.getSessionId(),
                    session.getAccountNumber(),
                    session.getLoginTime().toString().substring(0, 19))
            );
        }
    }

    @Override
    public boolean changeAdminPassword(String username, String oldPassword, String newPassword) {
        if (validateAdmin(username, oldPassword)) {
            return adminDAO.updateAdminPassword(username, newPassword);
        }
        return false;
    }
}