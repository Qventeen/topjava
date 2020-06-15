package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


import static java.util.stream.Collectors.*;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> userMeals = new ArrayList<>();
        Map<LocalDate, Integer> mealDateExcess = new HashMap<>();

        //Фильтрация по часам и группировка по датам
        meals.forEach(m -> {
            LocalDate date = m.getDateTime().toLocalDate();
            LocalTime time = m.getDateTime().toLocalTime();
            if(TimeUtil.isBetweenHalfOpen(time,startTime,endTime)){
                userMeals.add(m);
            }
            //Группировка каллорий по датам
            mealDateExcess.put(date,mealDateExcess.getOrDefault(date,0) + m.getCalories());
        });

        //Создание результирующего списка
        ArrayList<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        userMeals.forEach(m -> userMealWithExcesses.add(
                new UserMealWithExcess(
                        m.getDateTime(),
                        m.getDescription(),
                        m.getCalories(),
                        mealDateExcess.get(m.getDateTime().toLocalDate()) <= caloriesPerDay
                ))

        );

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //мапа группировки каллорий по датам
        Map<LocalDate,Integer> userMealDate = new HashMap<>();

        List<UserMeal> userMeal = meals.stream()
                //Производим подсчет каллорий для каждого элемента по датам
                .peek(x -> {
                    LocalDate tmpDate = x.getDateTime().toLocalDate();
                    userMealDate.put(
                            tmpDate,
                            userMealDate.getOrDefault(tmpDate,0) + x.getCalories()
                    );
                })
                //Оставляем только необходимые временные интервалы
                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(),startTime,endTime))
                //Производим сортировку
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .collect(toList());

                //Создаем из текущих элементов новые
        List<UserMealWithExcess> userMealWithExcesses = userMeal.stream()
                .map((x) -> {
                    UserMealWithExcess tmp = new UserMealWithExcess(
                            x.getDateTime(),
                            x.getDescription(),
                            x.getCalories(),
                            userMealDate.get(x.getDateTime().toLocalDate()) <= caloriesPerDay);
                    return tmp;
                    //Выводим данные в список
                }).collect(toList());

        return userMealWithExcesses;
    }
}
