package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class MealTestData {
    public final static TestMatcher<Meal> MEAL_MATCHER = TestMatcher.usingFieldsComparator();
    public final static int NOT_FOUND_ID = 500;
    public final static int ADMIN_ID = UserTestData.ADMIN_ID;
    public final static int USER_ID = UserTestData.USER_ID;
    public final static LocalDate START_OF_BORD_DATE = LocalDate.of(2020,1,30);
    public final static LocalDate END_OF_BORD_DATE = LocalDate.of(2020,1,31);

    private final static int DEFAULT_CALORIES = 1000;
    private final static int SEQ = ADMIN_ID;

    public final static Meal USER_MEAL = new Meal(SEQ + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 1), "Завтрак", 800);
    public final static Meal USER_MEAL_2 = new Meal(SEQ + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0), "Второй завтрак", 2000);

    public static final Meal ADMIN_MEAL = new Meal(SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Завтрак", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0), "Второй завтрак", 1000);

    public static final List<Meal> USER_MEALS_LIST = Arrays.asList(USER_MEAL, USER_MEAL_2);
    public static final List<Meal> ADMIN_MEALS_LIST = Arrays.asList(ADMIN_MEAL, ADMIN_MEAL_2);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS), "TestDescription", DEFAULT_CALORIES);
    }

    public static Meal getUpdated(){
        Meal updateMeal = new Meal(USER_MEAL);
        updateMeal.setCalories(350);
        updateMeal.setDescription("Updated description");
        updateMeal.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        return updateMeal;
    }
}
