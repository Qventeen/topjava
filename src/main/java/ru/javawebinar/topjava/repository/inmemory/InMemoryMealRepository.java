package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        //if user id is not contain in repository create new meal map for current user
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(
                userId,
                HashMap::new);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return userMeals.put(meal.getId(),meal);
        }

        return userMeals.computeIfPresent(meal.getId(),(id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId)
                .values()
                .stream()
                .sorted(comparing(Meal::getDate).reversed()).collect(toList());
    }



}

