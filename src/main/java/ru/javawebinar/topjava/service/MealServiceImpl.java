package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public final class MealServiceImpl implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServiceImpl.class);
    private MealRepository<Meal> repository;

    private Integer caloriesPerDay = 2000;

    public MealServiceImpl(MealRepository<Meal> repository) {
        this.repository = repository;
    }

    @Override
    public List<MealTo> getAll() {
        log.info("Get all elements from repository and create List of Meal transfer objects");
        return MealsUtil.filteredByStreams(repository.findAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    @Override
    public MealTo getById(Long id) {
        log.info("Get item by id and create MealTo");

        List<MealTo> result = MealsUtil.filteredByStreams(
                repository.findAll(),
                LocalTime.MIN,
                LocalTime.MAX,
                caloriesPerDay
        );


        final MealTo[] mealTo = {null};
        result.forEach(x -> {
            if(x.getId().equals(id)){
                mealTo[0] = x;
                return;
            }
        });
         return mealTo[0];
    }

    @Override
    public void add(Meal meal) {
        log.info("Add meal to repository");
        Meal result = repository.save(meal);
    }

    @Override
    public void update(Meal meal) {
        log.info("Update meal in repository");
        add(meal);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Delete meal from repository by id");
        if(repository.delete(id) == null)
            return false;
        return true;
    }

    public void setCaloriesPerDay(Integer caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

}
