package ru.javawebinar.topjava.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class DescriptionValidator implements Validator<String> {
    @Override
    public String validate(String validationE, List<String> validationR) {
        String result = "";
        if(Objects.nonNull(validationE))
            result = validationE;
        return result;
    }
}
