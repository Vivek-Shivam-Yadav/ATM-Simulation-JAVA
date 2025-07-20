package com.atm.service.impl;

import com.atm.service.UserService;
import com.atm.service.ATMService;
import com.atm.service.TransactionService;
import com.atm.model.Transaction;
import com.atm.dao.UserDAO;
import com.atm.dao.impl.UserDAOImpl;
import com.atm.model.User;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final ATMService atmService = new ATMServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private int failedAttempts = 0;
    private boolean isBlocked = false;

    @Override
    public boolean validateUser(String cardNumber, String pin) {
        if (isBlocked) {
            System.out.println("Account is blocked due to multiple failed login attempts.");
            return false;
        }

        User user = userDAO.getUserByCard(cardNumber);
        if (user != null && user.getPin().equals(pin) && "active".equals(user.getStatus())) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                isBlocked = true;
                if (user != null) {
                    userDAO.updateUserStatus(cardNumber, "blocked");
                }
                System.out.println("Account blocked due to 3 failed login attempts.");
            }
            return false;
        }
    }

    @Override
    public boolean changePin(String cardNumber, String oldPin, String newPin) {
        User user = userDAO.getUserByCard(cardNumber);
        if (user != null && user.getPin().equals(oldPin)) {
            return userDAO.updateUserPin(cardNumber, newPin);
        }
        return false;
    }

    @Override
    public String getAccountNumberByCard(String cardNumber) {
        User user = userDAO.getUserByCard(cardNumber);
        return user != null ? user.getAccountNumber() : null;
    }

    @Override
    public String getCardByAccount(String accountNumber) {
        User user = userDAO.getUserByAccount(accountNumber);
        return user != null ? user.getCardNumber() : null;
    }

    @Override
    public boolean blockUser(String cardNumber) {
        return userDAO.updateUserStatus(cardNumber, "blocked");
    }

    @Override
    public boolean unblockUser(String cardNumber) {
        isBlocked = false;
        failedAttempts = 0;
        return userDAO.updateUserStatus(cardNumber, "active");
    }

    public double getBalance(String accountNumber) {
        return atmService.checkBalance(accountNumber);
    }

    public boolean withdraw(String accountNumber, double amount) {
        boolean success = atmService.withdraw(accountNumber, amount);
        if (success) {
            Transaction transaction = transactionService.createTransaction(
                accountNumber, "WITHDRAWAL", amount, "SUCCESS");
            transactionService.recordTransaction(transaction);
        }
        return success;
    }

    public boolean deposit(String accountNumber, double amount) {
        boolean success = atmService.deposit(accountNumber, amount);
        if (success) {
            Transaction transaction = transactionService.createTransaction(
                accountNumber, "DEPOSIT", amount, "SUCCESS");
            transactionService.recordTransaction(transaction);
        }
        return success;
    }

    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
        boolean success = atmService.transferFunds(fromAccountNumber, toAccountNumber, amount);
        if (success) {
            Transaction debitTransaction = transactionService.createTransaction(
                fromAccountNumber, "TRANSFER_OUT", amount, "SUCCESS");
            debitTransaction = new Transaction(
                debitTransaction.getTransactionId(),
                fromAccountNumber,
                "TRANSFER_OUT",
                amount,
                debitTransaction.getTimestamp(),
                toAccountNumber,
                "SUCCESS"
            );

            Transaction creditTransaction = transactionService.createTransaction(
                toAccountNumber, "TRANSFER_IN", amount, "SUCCESS");
            creditTransaction = new Transaction(
                creditTransaction.getTransactionId(),
                toAccountNumber,
                "TRANSFER_IN",
                amount,
                creditTransaction.getTimestamp(),
                fromAccountNumber,
                "SUCCESS"
            );

            transactionService.recordTransaction(debitTransaction);
            transactionService.recordTransaction(creditTransaction);
        }
        return success;
    }

    public List<Transaction> getMiniStatement(String accountNumber) {
        return transactionService.getMiniStatement(accountNumber);
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionService.getTransactionHistory(accountNumber);
    }
}