package ru.javawebinar.topjava.util;

import org.slf4j.Logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class DateTimeUtil {
    private static final Logger log = getLogger(DateTimeUtil.class);

    /**
     * Тестирование на предмет соответствия периоду времени
     * @param lt Тестируемое время
     * @param startTime Начало периода
     * @param endTime Конец периода
     * @return Результат тестирования
     */
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm", Locale.getDefault());

    /**
     * Получение предопределенного форматера даты времени приложения
     * @return форматтер
     */
    public static DateTimeFormatter getDateTimeFormatter(){
        log.debug("Return date time formatter");
        return formatter;
    }
}
