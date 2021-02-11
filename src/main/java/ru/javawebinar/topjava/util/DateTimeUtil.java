package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, @Nullable LocalTime start, @Nullable LocalTime end) {
        return (start == null || lt.compareTo(start) >= 0)  && (end == null || lt.compareTo(end) < 0);
    }

    public static boolean isBetweenInclusive(LocalDate lt, @Nullable LocalDate start, @Nullable LocalDate end) {
        return (start == null || lt.compareTo(start) >= 0) && (end == null || lt.compareTo(end) <= 0);
    }

    public static LocalDate getDate(String reqDate){
        try {
            return LocalDate.parse(reqDate);
        }catch(Exception e){
            return null;
        }
    }

    public static LocalTime getTime(String reqTime){
        try{
            return LocalTime.parse(reqTime);
        } catch(Exception e){
            return null;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

