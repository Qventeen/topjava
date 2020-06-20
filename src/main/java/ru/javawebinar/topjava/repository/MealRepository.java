package ru.javawebinar.topjava.repository;

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
     * @exception UnsupportedOperationException
     */
    default T findById(Long id){
        throw new UnsupportedOperationException();
    }

    /**
     * Сохраняет элемент в репозитории
     * @param item элемент для сохранения
     * @return ссылку на сохраненный элемент
     * @exception UnsupportedOperationException
     */
    default T save(T item) {
        throw new UnsupportedOperationException();
    }

    /**
     * Удаляем элемент из репозитория
     * @param id идентификатор элемента
     * @return результат выполнения операции
     * @exception UnsupportedOperationException
     */
    default boolean delete(Long id){
        throw new UnsupportedOperationException();
    }

    /**
     * Проверяем наличие идентификатора в репозитории
     * @param id проверяемый идентификатор
     * @return результат поиска идентификатора
     * @exception UnsupportedOperationException
     */
    default boolean existById(Long id){
        throw new UnsupportedOperationException();
    }

}
