package ru.javawebinar.topjava.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IdValidator implements Validator<Long> {
private static final Logger log = LoggerFactory.getLogger(IdValidator.class);
    @Override
    public Long validate(String validationE, List<String> validationR) {
        log.debug("validate input ID of meal");
        Long id = null;
        try{
            id = Long.parseLong(validationE);
            if(id < 0) {
                validationR.add("ID of meal < 0");
                log.warn("ID of meal < 0 ({})", id);
            }
        }catch (Exception e){
            if( !validationE.isEmpty()) {
                log.warn("Incorrect ID of meal -> {}", id);
                validationR.add("Incorrect ID of meal");
            }
            return id;
        }
        return id;
    }
}
