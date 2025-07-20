package com.atm.service;

public interface UserService {
    boolean validateUser(String cardNumber, String pin);
    boolean changePin(String cardNumber, String oldPin, String newPin);
    String getAccountNumberByCard(String cardNumber);
    String getCardByAccount(String accountNumber);
    boolean blockUser(String cardNumber);
    boolean unblockUser(String cardNumber);
}