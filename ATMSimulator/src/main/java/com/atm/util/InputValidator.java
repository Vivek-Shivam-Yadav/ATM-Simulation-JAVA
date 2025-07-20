package com.atm.util;

import com.atm.exception.InvalidAmountException;

public class InputValidator {

    public boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    public boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("ACC\\d+");
    }

    public double validateAmount(String amountStr) throws InvalidAmountException {
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                throw new InvalidAmountException("Amount must be greater than zero.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Invalid amount format.");
        }
    }

    public int validateMenuChoice(String choiceStr, int min, int max) {
        try {
            int choice = Integer.parseInt(choiceStr);
            if (choice < min || choice > max) {
                return -1;
            }
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean isValidMobile(String mobile) {
        return mobile != null && mobile.matches("\\d{10}");
    }
}