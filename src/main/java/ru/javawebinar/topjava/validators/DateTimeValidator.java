package ru.javawebinar.topjava.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class DateTimeValidator implements Validator<LocalDateTime> {
    private static final Logger log = LoggerFactory.getLogger(DateTimeValidator.class);
    @Override
    public LocalDateTime validate(String validationE, List<String> validationR) {
        log.debug("validate of input DateTime {}", validationE);
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(validationE);
        } catch (Exception e){
            log.error("Incorrect parse input DateTime {}", e);
            validationR.add("Incorrect input DateTime");
            return LocalDateTime.now();
        }


        return dateTime;
    }
}
