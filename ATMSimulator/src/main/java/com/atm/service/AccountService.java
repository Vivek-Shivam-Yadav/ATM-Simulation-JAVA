package com.atm.service;

import com.atm.model.Account;

public interface AccountService {
    Account getAccountByNumber(String accountNumber);
    boolean validateAccount(String accountNumber);
    boolean updateBalance(String accountNumber, double newBalance);
    double getBalance(String accountNumber);
    boolean createAccount(Account account);
}