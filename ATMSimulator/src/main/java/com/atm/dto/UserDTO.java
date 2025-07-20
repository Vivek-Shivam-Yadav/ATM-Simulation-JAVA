package com.atm.dto;

public class UserDTO {

    private int userId;
    private String name;
    private String cardNumber;
    private String mobile;
    private String accountType;
    private String status;
    private String accountNumber;

    public UserDTO() {}

    public UserDTO(int userId, String name, String cardNumber, String mobile, 
                   String accountType, String status, String accountNumber) {
        this.userId = userId;
        this.name = name;
        this.cardNumber = cardNumber;
        this.mobile = mobile;
        this.accountType = accountType;
        this.status = status;
        this.accountNumber = accountNumber;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}