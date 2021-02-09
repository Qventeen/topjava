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

public class InMemoryMealRepository implements MealRepository<Meal> {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    private static MealRepository<Meal> instance;

    private AtomicLong counter = new AtomicLong(0);
    private ConcurrentMap<Long,Meal> repository;

    private InMemoryMealRepository(){
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
            instance = new InMemoryMealRepository();
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

        if(item.isNew()) {
            item.setId(counter.getAndAdd(1));
            return repository.put(item.getId(), item);
        }

        return repository.computeIfPresent(item.getId(),(id, oldMeal) -> item);
    }

    @Override
    public Meal delete(Long id) {
        return repository.remove(id);
    }

}
