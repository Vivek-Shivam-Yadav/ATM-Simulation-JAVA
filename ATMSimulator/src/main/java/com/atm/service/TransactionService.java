package com.atm.service;

import com.atm.model.Transaction;
import java.util.List;

public interface TransactionService {
    boolean recordTransaction(Transaction transaction);
    List<Transaction> getMiniStatement(String accountNumber);
    List<Transaction> getTransactionHistory(String accountNumber);
    String generateTransactionId();
    Transaction createTransaction(String accountNumber, String type, double amount, String status);
}