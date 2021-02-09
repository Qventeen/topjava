package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepositoryImpl implements MealRepository<Meal> {
    private static final Logger log = getLogger(InMemoryMealRepositoryImpl.class);
    private static MealRepository<Meal> instance;

    private volatile AtomicLong counter = new AtomicLong(0);
    private ConcurrentMap<Long,Meal> repository;

    private InMemoryMealRepositoryImpl(){
        repository = new ConcurrentSkipListMap<>();
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        repository.put(counter.get(), new Meal(counter.getAndAdd(1), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public static synchronized MealRepository<Meal> getInstance(){
        if(instance == null)
            instance = new InMemoryMealRepositoryImpl();
        return instance;
    }

    @Override
    public List<Meal> findAll() {
        log.debug("Get all items");
        List<Meal> result =  new ArrayList<>(repository.values());
        return Collections.unmodifiableList(result);
    }

    @Override
    public Meal findById(Long id) {
        log.debug("get item by id");
        return repository.get(id);
    }

    @Override
    public Meal save(Meal item) {
        log.debug("Create or update record");

        if(item == null)
        return null;

        Meal result;
        if(item.getId() != null && repository.containsKey(item.getId()))
            result = new Meal(item.getId(), item.getDateTime(),item.getDescription(),item.getCalories());
        else
            result = new Meal(counter.getAndAdd(1), item.getDateTime(), item.getDescription(), item.getCalories());

        repository.put(result.getId(),result);
        return result;
    }

    @Override
    public Meal delete(Long id) {

        return repository.remove(id);
    }

}
