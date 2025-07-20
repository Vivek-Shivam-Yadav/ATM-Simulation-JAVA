package com.atm.config;


import java.util.Properties;

public class ATMConfig {

    private static Properties properties = new Properties();
    private static boolean initialized = false;

    public static final String DB_URL = "jdbc:mysql://localhost:3306/atmdb";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "pass";

    public static final double MIN_WITHDRAWAL_AMOUNT = 20.0;
    public static final double MAX_WITHDRAWAL_AMOUNT = 5000.0;
    public static final double MIN_DEPOSIT_AMOUNT = 1.0;
    public static final int MAX_PIN_ATTEMPTS = 3;
    public static final int SESSION_TIMEOUT_MINUTES = 5;

    public static void initialize() {
        if (!initialized) {
            loadDefaultProperties();
            initialized = true;
        }
    }

    private static void loadDefaultProperties() {
        properties.setProperty("atm.name", "ABC Bank ATM");
        properties.setProperty("atm.location", "Main Branch");
        properties.setProperty("atm.version", "1.0.0");
        properties.setProperty("min.withdrawal.amount", "20.0");
        properties.setProperty("max.withdrawal.amount", "5000.0");
        properties.setProperty("min.deposit.amount", "1.0");
        properties.setProperty("max.pin.attempts", "3");
        properties.setProperty("session.timeout.minutes", "5");
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static double getDoubleProperty(String key, double defaultValue) {
        try {
            return Double.parseDouble(properties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}