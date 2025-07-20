package com.atm.service.impl;

import com.atm.model.Transaction;
import com.atm.service.TransactionService;
import com.atm.dao.TransactionDAO;
import com.atm.dao.impl.TransactionDAOImpl;
import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAOImpl();

    @Override
    public boolean recordTransaction(Transaction transaction) {
        return transactionDAO.createTransaction(transaction);
    }

    @Override
    public List<Transaction> getMiniStatement(String accountNumber) {
        return transactionDAO.getMiniStatement(accountNumber, 5);
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionDAO.getTransactionsByAccount(accountNumber);
    }

    @Override
    public String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public Transaction createTransaction(String accountNumber, String type, double amount, String status) {
        String transactionId = generateTransactionId();
        return new Transaction(transactionId, accountNumber, type, amount, 
                             LocalDateTime.now(), null, status);
    }
}