package com.atm.model;

import java.time.LocalDateTime;

public class Account {
    private String accountNumber;
    private int userId;
    private double balance;
    private LocalDateTime lastTransactionAt;

    public Account(String accountNumber, int userId, double balance) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance = balance;
        this.lastTransactionAt = LocalDateTime.now();
    }

    public String getAccountNumber() { return accountNumber; }
    public int getUserId() { return userId; }
    public double getBalance() { return balance; }
    public LocalDateTime getLastTransactionAt() { return lastTransactionAt; }

    public void setBalance(double balance) { 
        this.balance = balance;
        this.lastTransactionAt = LocalDateTime.now();
    }
    public void setLastTransactionAt(LocalDateTime lastTransactionAt) {
        this.lastTransactionAt = lastTransactionAt;
    }
}