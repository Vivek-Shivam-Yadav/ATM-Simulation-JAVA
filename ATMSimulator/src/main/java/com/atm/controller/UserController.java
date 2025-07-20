package com.atm.controller;

import com.atm.service.impl.UserServiceImpl;
import java.util.Scanner;

public class UserController {

    private final Scanner scanner = new Scanner(System.in);
    private final UserServiceImpl userService = new UserServiceImpl();

    public void authenticateUser() {
        System.out.print("Enter Card Number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (userService.validateUser(cardNumber, pin)) {
            System.out.println("Login successful.\n");
            String accountNumber = userService.getAccountNumberByCard(cardNumber);
            showUserMenu(accountNumber);
        } else {
            System.out.println("Authentication failed. Try again.");
        }
    }

    private void showUserMenu(String accountNumber) {
        while (true) {
            System.out.println("===============================");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Change PIN");
            System.out.println("6. Mini Statement");
            System.out.println("7. Transaction History");
            System.out.println("8. Exit");
            System.out.println("===============================");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        double balance = userService.getBalance(accountNumber);
                        System.out.printf("Your balance is: $%.2f%n", balance);
                    }
                    case 2 -> {
                        System.out.print("Enter amount to withdraw: $");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        boolean success = userService.withdraw(accountNumber, amount);
                        if (success) {
                            System.out.println("Withdrawal successful.");
                            System.out.printf("New balance: $%.2f%n", userService.getBalance(accountNumber));
                        } else {
                            System.out.println("Withdrawal failed. Check balance or limits.");
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter amount to deposit: $");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        boolean success = userService.deposit(accountNumber, amount);
                        if (success) {
                            System.out.println("Deposit successful.");
                            System.out.printf("New balance: $%.2f%n", userService.getBalance(accountNumber));
                        } else {
                            System.out.println("Deposit failed.");
                        }
                    }
                    case 4 -> {
                        System.out.print("Enter recipient account number: ");
                        String toAccount = scanner.nextLine();
                        System.out.print("Enter amount to transfer: $");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        boolean success = userService.transferFunds(accountNumber, toAccount, amount);
                        if (success) {
                            System.out.println("Fund transfer successful.");
                            System.out.printf("New balance: $%.2f%n", userService.getBalance(accountNumber));
                        } else {
                            System.out.println("Fund transfer failed. Check balance or account details.");
                        }
                    }
                    case 5 -> {
                        System.out.print("Enter old PIN: ");
                        String oldPin = scanner.nextLine();
                        System.out.print("Enter new PIN: ");
                        String newPin = scanner.nextLine();
                        boolean success = userService.changePin(userService.getCardByAccount(accountNumber), oldPin, newPin);
                        if (success) {
                            System.out.println("PIN changed successfully.");
                        } else {
                            System.out.println("PIN change failed. Incorrect old PIN.");
                        }
                    }
                    case 6 -> {
                        var miniStatement = userService.getMiniStatement(accountNumber);
                        System.out.println("\n========== Mini Statement ==========");
                        if (miniStatement.isEmpty()) {
                            System.out.println("No recent transactions.");
                        } else {
                            miniStatement.forEach(transaction -> {
                                System.out.printf("%-12s %-10s $%-8.2f %-10s%n",
                                    transaction.getTransactionId(),
                                    transaction.getType(),
                                    transaction.getAmount(),
                                    transaction.getStatus());
                            });
                        }
                        System.out.println("===================================");
                    }
                    case 7 -> {
                        var history = userService.getTransactionHistory(accountNumber);
                        System.out.println("\n======== Transaction History ========");
                        if (history.isEmpty()) {
                            System.out.println("No transactions found.");
                        } else {
                            history.forEach(transaction -> {
                                System.out.printf("%-12s %-15s %-10s $%-8.2f %-10s%n",
                                    transaction.getTransactionId(),
                                    transaction.getTimestamp().toLocalDate(),
                                    transaction.getType(),
                                    transaction.getAmount(),
                                    transaction.getStatus());
                            });
                        }
                        System.out.println("=====================================");
                    }
                    case 8 -> {
                        System.out.println("Session ended. Thank you!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }
}