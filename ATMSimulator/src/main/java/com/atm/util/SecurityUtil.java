package com.atm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final SecureRandom random = new SecureRandom();

    public static String hashPin(String pin, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt.getBytes());
            byte[] hashedPin = md.digest(pin.getBytes());
            return Base64.getEncoder().encodeToString(hashedPin);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available", e);
        }
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static boolean verifyPin(String pin, String hashedPin, String salt) {
        return hashPin(pin, salt).equals(hashedPin);
    }

    public static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return "****-****-****-****";
        }
        return cardNumber.substring(0, 4) + "-****-****-" + cardNumber.substring(12);
    }

    public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + 
               String.format("%04d", random.nextInt(10000));
    }

    public static String generateSessionId() {
        return "SES" + System.currentTimeMillis() + 
               String.format("%04d", random.nextInt(10000));
    }
}