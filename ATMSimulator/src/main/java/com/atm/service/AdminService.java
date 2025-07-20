package com.atm.service;

public interface AdminService {
    boolean validateAdmin(String username, String password);
    void refillCash(int denomination, int count);
    int checkATMCash();
    void viewUserLogs();
    boolean changeAdminPassword(String username, String oldPassword, String newPassword);
}