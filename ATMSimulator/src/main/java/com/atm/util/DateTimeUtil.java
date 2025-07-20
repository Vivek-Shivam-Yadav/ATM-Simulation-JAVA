package com.atm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DISPLAY_DATETIME_FORMAT = "MMM dd, yyyy HH:mm";

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    public static String formatDateTimeForDisplay(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DISPLAY_DATETIME_FORMAT));
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    public static boolean isExpired(LocalDateTime dateTime, int minutesToExpire) {
        return LocalDateTime.now().isAfter(dateTime.plusMinutes(minutesToExpire));
    }
}