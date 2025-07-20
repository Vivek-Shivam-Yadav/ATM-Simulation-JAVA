package com.atm.model;

import java.time.LocalDateTime;

public class SessionLog {
    private String sessionId;
    private String accountNumber;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String status;

    public SessionLog(String sessionId, String accountNumber, LocalDateTime loginTime, String status) {
        this.sessionId = sessionId;
        this.accountNumber = accountNumber;
        this.loginTime = loginTime;
        this.status = status;
    }

    public String getSessionId() { return sessionId; }
    public String getAccountNumber() { return accountNumber; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public String getStatus() { return status; }

    public void setLogoutTime(LocalDateTime logoutTime) { this.logoutTime = logoutTime; }
    public void setStatus(String status) { this.status = status; }
}