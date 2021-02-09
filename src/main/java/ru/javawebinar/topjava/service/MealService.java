package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {

    /**
     * Получить все элементы репозитория из репозитория
     * @return Список Meal transfer object
     */
    List<MealTo> getAll();

    /**
     * Получает элемент по идентификатору
     * @param id идентификатор
     * @return List of Meal transfer object
     */
    Meal get(Long id);

    /**
     * Добавляет новый элемент в ерпозиторий
     * @param meal Еда
     */
    void add(Meal meal);

    /**
     * Обновляет существующий элемент в репозитории
     * @param meal Еда
     */
    void update(Meal meal);

    /**
     * Удаляет элемент из репозитория
     * @param id идентификатор
     * @return
     */
    boolean delete(Long id);
}
