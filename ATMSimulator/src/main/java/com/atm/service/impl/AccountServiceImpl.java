package com.atm.service.impl;

import com.atm.model.Account;
import com.atm.service.AccountService;
import com.atm.dao.AccountDAO;
import com.atm.dao.impl.AccountDAOImpl;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber);
    }

    @Override
    public boolean validateAccount(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber) != null;
    }

    @Override
    public boolean updateBalance(String accountNumber, double newBalance) {
        if (newBalance < 0) return false;
        return accountDAO.updateAccountBalance(accountNumber, newBalance);
    }

    @Override
    public double getBalance(String accountNumber) {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    @Override
    public boolean createAccount(Account account) {
        return accountDAO.createAccount(account);
    }
}