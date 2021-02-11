package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public interface MealRepository {
    // null if not found, when updated
    Meal save(int userId, Meal meal);

    // false if not found
    boolean delete(int userId, int id);

    // null if not found
    Meal get(int userId, int id);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAll(int userId, Comparator<Meal> comparator);

    Collection<Meal> getByFilter(int userId, Predicate<Meal> filter);

    Collection<Meal> getByFilter (int userId, Predicate<Meal> filter, Comparator<Meal> comparator);
}
