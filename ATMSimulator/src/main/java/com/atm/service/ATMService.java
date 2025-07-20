package com.atm.service;

public interface ATMService {
    double checkBalance(String accountNumber);
    boolean withdraw(String accountNumber, double amount);
    boolean deposit(String accountNumber, double amount);
    boolean transferFunds(String fromAccountNumber, String toAccountNumber, double amount);
    boolean validateWithdrawalLimit(String accountNumber, double amount);
}