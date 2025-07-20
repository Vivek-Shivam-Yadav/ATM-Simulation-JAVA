package com.atm.dao;

import com.atm.model.Account;
import java.util.List;

public interface AccountDAO {
    Account getAccountByNumber(String accountNumber);
    boolean updateAccountBalance(String accountNumber, double newBalance);
    boolean createAccount(Account account);
    List<Account> getAllAccounts();
    boolean deleteAccount(String accountNumber);
}