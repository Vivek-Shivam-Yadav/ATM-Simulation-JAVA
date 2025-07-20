package com.atm.controller;

import java.util.Scanner;

public class ATMController {

    private final Scanner scanner = new Scanner(System.in);
    private final UserController userController = new UserController();
    private final AdminController adminController = new AdminController();

    public void startSession() {
        boolean running = true;

        while (running) {
            try {
                System.out.println("\nInsert your card to continue...");
                System.out.println("1. User Login");
                System.out.println("2. Admin Login"); 
                System.out.println("3. Exit");
                System.out.print("Choose: ");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> userController.authenticateUser();
                    case 2 -> adminController.adminLogin();
                    case 3 -> {
                        running = false;
                        System.out.println("Thank you for using ABC Bank ATM.");
                    }
                    default -> System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.err.println("System error occurred. Please try again.");
                scanner.nextLine();
            }
        }
    }
}