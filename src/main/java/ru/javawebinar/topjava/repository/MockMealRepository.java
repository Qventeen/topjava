package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MockMealRepository implements MealRepository<Meal> {
    private static final Logger log = LoggerFactory.getLogger(MockMealRepository.class);

    private static MealRepository<Meal> instance;

    private final List<Meal> mealList;


    public static synchronized MealRepository<Meal> getInstance(){
        log.debug("Get instance of repository");
        if(instance == null) instance = new MockMealRepository();
        return instance;
    }

    private MockMealRepository() {
        mealList = Collections.synchronizedList(Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }


    @Override
    public List<Meal> findAll() {
        return this.mealList;
    }
}
