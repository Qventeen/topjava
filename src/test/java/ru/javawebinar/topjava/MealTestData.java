package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MealTestData {
    public final static int NOT_FOUND_ID = 500;
    public final static int ADMIN_ID = UserTestData.ADMIN_ID;
    public final static int USER_ID = UserTestData.USER_ID;
    public final static LocalDate START_OF_BORD_DATE = LocalDate.of(2020,1,30);
    public final static LocalDate END_OF_BORD_DATE = LocalDate.of(2020,1,31);

    private final static int DEFAULT_CALORIES = 1000;
    private final static int SEQ = ADMIN_ID;

    public final static Meal USER_MEAL = new Meal(
            SEQ + 1,
            LocalDateTime.of(2020, Month.JANUARY, 31, 0, 1),
            "Завтрак",
            800);
    public final static Meal USER_MEAL_2 = new Meal(
            SEQ + 2,
            LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0),
            "Второй завтрак", 2000);


    public static final Meal ADMIN_MEAL = new Meal(
            SEQ + 3,
            LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
            "Завтрак", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(
            SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0),
            "Второй завтрак", 1000);

    public static Meal getNew() {
        return new Meal(
                LocalDateTime.now(),
                "TestDescription",
                DEFAULT_CALORIES);
    }

    public static Meal getUpdated(Meal meal){
        Meal updateMeal = new Meal(meal);
        updateMeal.setCalories(350);
        updateMeal.setDescription("Updated description");
        updateMeal.setDateTime(LocalDateTime.now().plus(3, ChronoUnit.HOURS));
        return updateMeal;
    }

    public static void assertMatch(Meal actual, Meal expect){
        assertThat(actual).isEqualToComparingFieldByField(expect);

    }

    public static void assertNotFoundMatch(ThrowableAssert.ThrowingCallable codeForTest) {
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(codeForTest);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal ... expect){
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(expect));
    }
}
