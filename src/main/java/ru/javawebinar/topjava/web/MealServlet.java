package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MockMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MockMealServiceImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealService service;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.debug("Add list of meals to request");
        List<MealTo> meals = service.getAll();
        req.setAttribute("meals", meals);
        req.setAttribute("formatter", DateTimeUtil.getDateTimeFormatter());

        log.debug("Redirect to meals.jsp");
        getServletContext().getRequestDispatcher("/meals.jsp").forward(req,resp);
    }

    @Override
    public void init() throws ServletException {
        log.debug("Init {}", this.getClass().getSimpleName());
        service = new MockMealServiceImpl(MockMealRepository.getInstance());
    }
}
