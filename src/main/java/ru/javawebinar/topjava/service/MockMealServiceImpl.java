package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public final class MockMealServiceImpl implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MockMealServiceImpl.class);
    private MealRepository<Meal> repository;

    private Integer caloriesPerDay = 2000;

    public MockMealServiceImpl(MealRepository<Meal> repository) {
        this.repository = repository;
    }

    @Override
    public List<MealTo> getAll() {
        log.debug("Get all elements from repository and create List of Meal transfer objects");
        return MealsUtil.filteredByStreams(repository.findAll(), LocalTime.MIN,LocalTime.MAX,caloriesPerDay);
    }

    public void setCaloriesPerDay(Integer caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

}
