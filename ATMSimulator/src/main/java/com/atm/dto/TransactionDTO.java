package com.atm.dto;

public class TransactionDTO {

    private String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private String timestamp;
    private String targetAccount;
    private String status;

    public TransactionDTO() {}

    public TransactionDTO(String transactionId, String accountNumber, String type, double amount,
                         String timestamp, String targetAccount, String status) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.targetAccount = targetAccount;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getTargetAccount() { return targetAccount; }
    public void setTargetAccount(String targetAccount) { this.targetAccount = targetAccount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}