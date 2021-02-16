package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static ru.javawebinar.topjava.MealTestData.*;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(value = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealRepository repository;

    @Autowired
    MealService service;


    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }


    @Test
    public void updateOwnMeal() {
        Meal updatedU = getUpdated(USER_MEAL);
        service.update(updatedU, USER_ID);

        assertMatch(service.get(updatedU.getId(), USER_ID),updatedU);
    }

    @Test
    public void updateNotOwnMeal() {
        Meal updatedU = getUpdated(USER_MEAL);
        assertNotFoundMatch(() -> service.update(updatedU, ADMIN_ID));
    }

    @Test
    public void deleteOwn() {
        int mealId = USER_MEAL.getId();
        service.delete(mealId, USER_ID);
        assertNull(repository.get(mealId, USER_ID));
    }

    @Test
    public void deleteNotOwn(){
        assertNotFoundMatch(() -> service.delete(USER_MEAL.getId(), ADMIN_ID));
    }

    @Test
    public void getOwn() {
        Meal actual = service.get(USER_MEAL.getId(), USER_ID);
        assertMatch(actual, USER_MEAL);
    }

    @Test
    public void getNotOwn(){
        assertNotFoundMatch(() -> service.get(USER_MEAL.getId(), ADMIN_ID));
    }

    @Test
    public void getNotFound(){
        assertNotFoundMatch(() -> service.get(NOT_FOUND_ID, ADMIN_ID));
    }

    @Test
    public void getAllOwn() {
        List<Meal> actual = service.getAll(ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL, ADMIN_MEAL_2);

        actual = service.getAll(USER_ID);
        assertMatch(actual, USER_MEAL, USER_MEAL_2);
    }

    @Test
    public void getAllNotFoundOwn(){
        List<Meal> actual = service.getAll(NOT_FOUND_ID);
        assertThat(actual).isEmpty();
    }

    @Test
    public void getBetweenInclusive() {
        //without bord
        List<Meal> actual = service.getBetweenInclusive(null,null, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL, ADMIN_MEAL_2);


        //Date bord testing
        actual = service.getBetweenInclusive(START_OF_BORD_DATE, START_OF_BORD_DATE, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL_2);

        actual = service.getBetweenInclusive(START_OF_BORD_DATE, END_OF_BORD_DATE, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL, ADMIN_MEAL_2);

        actual = service.getBetweenInclusive(END_OF_BORD_DATE, START_OF_BORD_DATE, ADMIN_ID);
        assertThat(actual).isEmpty();


    }






}
