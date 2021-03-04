package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    private static final Logger log = getLogger(JpaMealRepository.class);
    @PersistenceContext
    private EntityManager em;


    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            Meal testMeal = em.find(Meal.class, meal.getId());
            if (testMeal.getUser().getId() == userId) {
                meal.setUser(ref);
                return em.merge(meal);
            }
            return null;
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        log.debug("Delete Meal by id {} for userId {}", id, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("userId", userId)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        log.debug("Get meal by ID {} of certain users {}", id, userId);
        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("userId", userId)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.debug("Get all elements of user by ID {}", userId);
        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        log.debug("Get Meals between dates");
        return em.createNamedQuery(Meal.GET_BETWEEN_HALF_OPEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("sDT", startDateTime)
                .setParameter("eDT", endDateTime)
                .getResultList();
    }
}