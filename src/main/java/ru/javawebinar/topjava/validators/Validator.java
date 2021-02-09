package ru.javawebinar.topjava.validators;

import java.util.List;

public interface Validator<T> {
    T validate(String validationE, List<String> validationR);
}
