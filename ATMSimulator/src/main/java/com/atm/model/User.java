package com.atm.model;

public class User {
    private int userId;
    private String name;
    private String cardNumber;
    private String pin;
    private String mobile;
    private String accountType;
    private String status;
    private String accountNumber;

    public User(int userId, String name, String cardNumber, String pin, String mobile, String accountType, String status) {
        this.userId = userId;
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.mobile = mobile;
        this.accountType = accountType;
        this.status = status;
        this.accountNumber = "ACC" + userId * 111;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public String getMobile() { return mobile; }
    public String getAccountType() { return accountType; }
    public String getStatus() { return status; }
    public String getAccountNumber() { return accountNumber; }

    public void setPin(String pin) { this.pin = pin; }
    public void setStatus(String status) { this.status = status; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}