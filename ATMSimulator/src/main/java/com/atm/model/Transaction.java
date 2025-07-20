package com.atm.model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String targetAccount;
    private String status;

    public Transaction(String transactionId, String accountNumber, String type, double amount, 
                      LocalDateTime timestamp, String targetAccount, String status) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.targetAccount = targetAccount;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getTargetAccount() { return targetAccount; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("ID: %s, Type: %s, Amount: $%.2f, Status: %s, Date: %s",
            transactionId, type, amount, status, timestamp.toLocalDate());
    }
}