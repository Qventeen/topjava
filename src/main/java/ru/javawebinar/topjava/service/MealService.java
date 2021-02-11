package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);
    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        log.info("create {}", meal);
        return repository.save(userId, meal);
    }

    public void update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
        log.info("update {}", meal);
    }

    public void delete(int userId, int id) {
        log.info("delete by id {}", id);
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public Collection<Meal> getAllByFilter(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getByFilter -> from {} to {}", startDate, endDate);
        //Get records from BD between dates
        return repository.getByFilter(userId, meal ->
                DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate));
    }

    public Collection<Meal> getAll(int userId) {
        log.info("getAll for user #{}", userId);
        return repository.getAll(userId);
    }





}