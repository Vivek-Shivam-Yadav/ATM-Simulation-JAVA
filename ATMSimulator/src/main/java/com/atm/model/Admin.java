package com.atm.model;

import java.time.LocalDateTime;

public class Admin {
    private int adminId;
    private String username;
    private String password;
    private String role;
    private LocalDateTime lastLogin;

    public Admin(int adminId, String username, String password, String role) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLogin = LocalDateTime.now();
    }

    public int getAdminId() { return adminId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public LocalDateTime getLastLogin() { return lastLogin; }

    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
}