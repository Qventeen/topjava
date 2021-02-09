package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealService service;

    private final String TO_MEALS = "/meals.jsp";
    private final String TO_MEALS_CONTROLLER = "meals";
    private final String MEAL_FORM = "/mealForm.jsp";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        List<MealTo> meals = null;
        String url = TO_MEALS;

        switch(action == null? "all" : action) {
            case "delete":
                log.info("delete element");
                service.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(TO_MEALS_CONTROLLER);
                return;
            case "edit":
                log.info("edit element");
                req.setAttribute("meal", service.get(Long.parseLong(req.getParameter("id"))));
                url = MEAL_FORM;
                break;
            case "create":
                req.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 2000));
                url = MEAL_FORM;
                break;
            case "all":
                log.info("Get all elements from repository");
                req.setAttribute("meals", service.getAll());
                break;
            default:
                resp.sendRedirect(TO_MEALS_CONTROLLER);
                return;
        }

        log.info("Forward to {}", url);
        getServletContext().getRequestDispatcher(url).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        log.info("doPost");
        String url = TO_MEALS_CONTROLLER;
        String id = req.getParameter("id");

        Meal meal = new Meal(
                id.isEmpty() ? null : Long.parseLong(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        );

        if (meal.getId() == null) service.add(meal);
        else service.update(meal);
        resp.sendRedirect(url);
    }


    @Override
    public void init() throws ServletException {
        log.info("Init {}", this.getClass().getSimpleName());
        service = new MealServiceImpl(InMemoryMealRepository.getInstance());

        getServletContext().setAttribute("formatter", DateTimeUtil.getDateTimeFormatter());
    }

}
