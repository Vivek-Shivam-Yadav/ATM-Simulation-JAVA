package com.atm.dao;

import com.atm.model.Transaction;
import java.util.List;

public interface TransactionDAO {
    boolean createTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccount(String accountNumber);
    List<Transaction> getMiniStatement(String accountNumber, int limit);
    Transaction getTransactionById(String transactionId);
    List<Transaction> getAllTransactions();
}