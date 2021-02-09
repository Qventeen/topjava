package ru.javawebinar.topjava.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CaloriesValidator implements Validator<Integer> {
    private static final Logger log = LoggerFactory.getLogger(CaloriesValidator.class);
    @Override
    public synchronized Integer validate(String validationE, List<String> validationR) {
        log.debug("Validate of input calories");
        Integer result = 0;
        try{
            result = Integer.parseInt(validationE);
            if(result < 0){
                log.error("Value of calories < 0 ({})", result);
                validationR.add("Value of calories < 0");
            }
        } catch (Exception e){
            log.error("Incorrect value of calories -> {}", validationE);
            validationR.add("Incorrect value of calories");
            return result;
        }
        return result;
    }
}
