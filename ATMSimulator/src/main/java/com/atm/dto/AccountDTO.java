package com.atm.dto;

public class AccountDTO {

    private String accountNumber;
    private int userId;
    private double balance;
    private String lastTransactionAt;

    public AccountDTO() {}

    public AccountDTO(String accountNumber, int userId, double balance, String lastTransactionAt) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance = balance;
        this.lastTransactionAt = lastTransactionAt;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getLastTransactionAt() { return lastTransactionAt; }
    public void setLastTransactionAt(String lastTransactionAt) { this.lastTransactionAt = lastTransactionAt; }
}