package com.atm.controller;

import com.atm.service.impl.AdminServiceImpl;
import java.util.Scanner;

public class AdminController {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminServiceImpl adminService = new AdminServiceImpl();

    public void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (adminService.validateAdmin(username, password)) {
            System.out.println("Admin login successful.");
            showAdminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void showAdminMenu() {
        boolean adminSession = true;

        while (adminSession) {
            System.out.println("\n====== Admin Menu ======");
            System.out.println("1. Check ATM Cash");
            System.out.println("2. Refill ATM");
            System.out.println("3. View User Logs");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        int cash = adminService.checkATMCash();
                        System.out.println("ATM Cash Available: $" + cash);
                    }
                    case 2 -> {
                        System.out.print("Enter denomination to refill: $");
                        int denomination = scanner.nextInt();
                        System.out.print("Enter count of notes: ");
                        int count = scanner.nextInt();
                        scanner.nextLine();
                        adminService.refillCash(denomination, count);
                        System.out.println("Cash refilled successfully.");
                        System.out.println("Total ATM Cash: $" + adminService.checkATMCash());
                    }
                    case 3 -> {
                        System.out.println("User session logs:");
                        adminService.viewUserLogs();
                    }
                    case 4 -> {
                        System.out.println("Admin session ended.");
                        adminSession = false;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }
}