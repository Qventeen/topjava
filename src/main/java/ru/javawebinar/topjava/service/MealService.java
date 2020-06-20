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
     * @return Meal transfer object
     * @exception UnsupportedOperationException
     */
    default MealTo getById(Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Добавляет новый элемент в ерпозиторий
     * @param meal Еда
     * @exception UnsupportedOperationException
     */
    default void add(Meal meal){
        throw new UnsupportedOperationException();
    }

    /**
     * Обновляет существующий элемент в репозитории
     * @param meal Еда
     * @exception UnsupportedOperationException
     */
    default boolean update(Meal meal){
        throw new UnsupportedOperationException();
    }

    /**
     * Удаляет элемент из репозитория
     * @param id идентификатор
     * @exception UnsupportedOperationException
     */
    default boolean delete(Long id){
        throw new UnsupportedOperationException();
    }
}
