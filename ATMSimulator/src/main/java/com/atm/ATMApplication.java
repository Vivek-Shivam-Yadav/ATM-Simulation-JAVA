package com.atm;

import com.atm.controller.ATMController;
import com.atm.config.ATMConfig;
import com.atm.util.DatabaseUtil;

public class ATMApplication {

    public static void main(String[] args) {
        try {
            ATMConfig.initialize();
            DatabaseUtil.initializeDatabase();

            System.out.println("=================================");
            System.out.println("||   WELCOME TO ABC BANK ATM   ||");
            System.out.println("=================================");

            ATMController atmController = new ATMController();
            atmController.startSession();

        } catch (Exception e) {
            System.err.println("System Error: ATM is currently unavailable. Please try again later.");
        } finally {
            DatabaseUtil.closeConnection();
        }
    }
}
