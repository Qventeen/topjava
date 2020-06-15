package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.*;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mealDateExcess = new HashMap<>();
        List<Meal> userMeals = new ArrayList<>();
        //Фильтрация по часам и группировка по датам
        meals.forEach(m -> {
            if(TimeUtil.isBetweenHalfOpen(m.getTime(),startTime,endTime)){
                userMeals.add(m);
            }
            //Группировка каллорий по датам
            mealDateExcess.put(m.getDate(),mealDateExcess.getOrDefault(m.getDate(),0) + m.getCalories());
        });

        //Создание результирующего списка
        ArrayList<MealTo> userMealWithExcesses = new ArrayList<>();
        userMeals.forEach(m -> userMealWithExcesses.add(
                createTo(m,mealDateExcess.get(m.getDate()) < caloriesPerDay )
        ));

        return userMealWithExcesses;
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> sumMealByDate = meals.stream().collect(
                groupingBy(Meal::getDate,summingInt(Meal::getCalories))
        );

        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getTime(),startTime,endTime))
                .map(m -> createTo(m,sumMealByDate.get(m.getDate()) < caloriesPerDay))
                .collect(toList());
    }

    public static MealTo createTo(Meal meal, boolean excess ){
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
