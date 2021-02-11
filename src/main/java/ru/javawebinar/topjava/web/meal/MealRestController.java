package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal){
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(),meal);
    }

    public void update(int id, Meal meal){
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id){
        log.info("delete {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id){
        log.info("get by id {}", id);
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<MealTo> get(
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime
    ){
        log.info("get date from {} to {} time from {} to {}",
                startDate, endDate, startTime, endTime);

        Collection<Meal> meals = service.getAllByFilter(
                SecurityUtil.authUserId(),
                Objects.isNull(startDate)?
                        LocalDate.MIN:
                        startDate,
                Objects.isNull(endDate)?
                        LocalDate.MAX:
                        endDate
        );
        return MealsUtil.getFilteredTos(meals,
                SecurityUtil.authUserCaloriesPerDay(),
                Objects.isNull(startTime)? LocalTime.MIN: startTime,
                Objects.isNull(endTime)? LocalTime.MAX: endTime);

    }

    public List<MealTo> getAll(){
        log.info("getAll");
        return MealsUtil.getTos(
                service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()
        );
    }
}