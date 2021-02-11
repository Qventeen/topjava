package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;


import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}" , meal);
        //if user id is not contain in repository create new meal map for current user
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(
                userId,
                ConcurrentHashMap::new);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return userMeals.put(meal.getId(),meal);
        }

        return userMeals.computeIfPresent(meal.getId(),(id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAll(userId, comparing(Meal::getDate).reversed());
    }


    @Override
    public Collection<Meal> getAll(int userId, Comparator<Meal> comparator) {
        return getByFilter(userId, meal-> true, comparator);
    }

    @Override
    public Collection<Meal> getByFilter(int userId, Predicate<Meal> filter) {
        return getByFilter(userId, filter, comparing(Meal::getDate).reversed());
    }

    @Override
    public Collection<Meal> getByFilter(int userId, Predicate<Meal> filter, Comparator<Meal> comparator) {
        log.info("getAll for user {}", userId);
        return repository.getOrDefault(userId, new HashMap<>(0))
                .values()
                .stream()
                .filter(filter)
                .sorted(comparator)
                .collect(toList());
    }

}

