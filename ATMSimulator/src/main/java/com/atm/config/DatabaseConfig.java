package com.atm.config;

public class DatabaseConfig {

    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/atmdb?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "15670";

    public static final int CONNECTION_TIMEOUT = 30;
    public static final int MAX_CONNECTIONS = 10;
    public static final boolean AUTO_COMMIT = false;

    public static String getConnectionUrl() {
        return DATABASE_URL;
    }

    public static String getDriverClass() {
        return DRIVER_CLASS;
    }

    public static String getUsername() {
        return DATABASE_USER;
    }

    public static String getPassword() {
        return DATABASE_PASSWORD;
    }
}