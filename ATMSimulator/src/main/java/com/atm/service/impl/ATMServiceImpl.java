package com.atm.service.impl;

import com.atm.service.ATMService;
import com.atm.service.AccountService;
import com.atm.model.Account;

public class ATMServiceImpl implements ATMService {

    private final AccountService accountService = new AccountServiceImpl();
    private static final double DAILY_WITHDRAWAL_LIMIT = 5000.0;
    private static final double MIN_WITHDRAWAL_AMOUNT = 20.0;
    private static final double MIN_DEPOSIT_AMOUNT = 1.0;

    @Override
    public double checkBalance(String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    @Override
    public boolean withdraw(String accountNumber, double amount) {
        if (amount < MIN_WITHDRAWAL_AMOUNT || amount % 20 != 0) {
            return false;
        }

        Account account = accountService.getAccountByNumber(accountNumber);
        if (account != null && account.getBalance() >= amount && 
            validateWithdrawalLimit(accountNumber, amount)) {
            return accountService.updateBalance(accountNumber, account.getBalance() - amount);
        }
        return false;
    }

    @Override
    public boolean deposit(String accountNumber, double amount) {
        if (amount < MIN_DEPOSIT_AMOUNT) {
            return false;
        }

        Account account = accountService.getAccountByNumber(accountNumber);
        if (account != null) {
            return accountService.updateBalance(accountNumber, account.getBalance() + amount);
        }
        return false;
    }

    @Override
    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountService.getAccountByNumber(fromAccountNumber);
        Account toAccount = accountService.getAccountByNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null || amount <= 0) {
            return false;
        }

        if (fromAccount.getBalance() < amount) {
            return false;
        }

        boolean debitSuccess = accountService.updateBalance(fromAccountNumber, fromAccount.getBalance() - amount);
        if (debitSuccess) {
            boolean creditSuccess = accountService.updateBalance(toAccountNumber, toAccount.getBalance() + amount);
            if (!creditSuccess) {
                accountService.updateBalance(fromAccountNumber, fromAccount.getBalance());
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean validateWithdrawalLimit(String accountNumber, double amount) {
        return amount <= DAILY_WITHDRAWAL_LIMIT;
    }
}