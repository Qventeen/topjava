package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository<T> {
    /**
     * Возвращает все записи данного репозитория
     * @return
     */
    List<T> findAll();

    /**
     * Ищет элемент по id в текущем репозитории
     * @param id идентификатор элемента в репозитории
     * @return элемент
     */
    T findById(Long id);

    /**
     * Сохраняет элемент в репозитории
     * @param item элемент для сохранения
     * @return ссылку на сохраненный элемент
     */
    T save(T item);

    /**
     * Удаляем элемент из репозитория
     * @param id идентификатор элемента
     * @return результат выполнения операции
     */
    Meal delete(Long id);

}
