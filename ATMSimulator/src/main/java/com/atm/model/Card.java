package com.atm.model;

public class Card {
    private String cardNumber;
    private String pin;
    private String expiryDate;
    private String status;

    public Card(String cardNumber, String pin, String expiryDate) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.expiryDate = expiryDate;
        this.status = "active";
    }

    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public String getExpiryDate() { return expiryDate; }
    public String getStatus() { return status; }

    public void setPin(String pin) { this.pin = pin; }
    public void setStatus(String status) { this.status = status; }
}