package com.atm.dto;

public class AdminDTO {

    private int adminId;
    private String username;
    private String role;
    private String lastLogin;

    public AdminDTO() {}

    public AdminDTO(int adminId, String username, String role, String lastLogin) {
        this.adminId = adminId;
        this.username = username;
        this.role = role;
        this.lastLogin = lastLogin;
    }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
}