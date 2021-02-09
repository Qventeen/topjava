package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.validators.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealService service;

    private final String TO_MEALS = "/meals.jsp";
    private final String TO_MEALS_CONTROLLER = "meals";
    private final String TO_INSERT_OR_EDIT_MEAL = "/insertOrEditMeal.jsp";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> validErr =  new ArrayList<>();

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        List<MealTo> meals = Collections.EMPTY_LIST;
        String forward = TO_MEALS;

        if("delete".equalsIgnoreCase(action)){
            log.info("delete element");

            Long id = new IdValidator().validate(req.getParameter("id"), validErr);

            //Если идентификатор валидный выполняем удаление
            if(validErr.isEmpty())
                service.delete(id);

            //Получаем все элементы резозитория
            meals = service.getAll();

        } else if("edit".equalsIgnoreCase(action)){
            log.info("Get element byId");

            Long id = new IdValidator().validate(req.getParameter("id"), validErr);

            //Если идентификатор валидный получаем экземпляр еды из репозитория
            if(validErr.isEmpty())
                req.setAttribute("meal", service.getById(id));
            forward = TO_INSERT_OR_EDIT_MEAL;

        } else if(action == null){
            log.info("Get all elements from repository");
            meals = service.getAll();

        } else if("find".equalsIgnoreCase(action)){
            log.info("Find element by id in repository");

            Long id = new IdValidator().validate(req.getParameter("id"), validErr);

            MealTo mealTo = service.getById(id);
            if(mealTo != null)
                meals = Arrays.asList(mealTo);
        }
        else {
            LocalDateTime tmp = LocalDateTime.now();
            LocalDateTime dt = LocalDateTime.of(tmp.getYear(),tmp.getMonth(),tmp.getDayOfMonth(),tmp.getHour(),tmp.getMinute());
            req.setAttribute("meal", new Meal(null, dt,"",500));
            forward = TO_INSERT_OR_EDIT_MEAL;
        }

        req.setAttribute("validationR", validErr);
        req.setAttribute("meals", meals);

        log.info("Forward to {}", forward);
        getServletContext().getRequestDispatcher(forward).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> validErr =  new ArrayList<>();
        req.setCharacterEncoding("UTF-8");

        log.info("doPost");
        String forward = TO_MEALS_CONTROLLER;

        Meal meal = validateAndGetMealFromRequest(validErr, req);

        log.debug("Meal request validation -> {}", meal);
        req.setAttribute("validationR", validErr);

        //Если валидация не успешна переходим на страницу формы
        if(!validErr.isEmpty()) {
            forward = TO_INSERT_OR_EDIT_MEAL;
            req.getRequestDispatcher(forward).forward(req, resp);
        } else {
            //Если идентификатора нет значит новый элемент
            if (meal.getId() == null) service.add(meal);

            //Если идентификатор есть значит обновляем существующий элемент
            else service.update(meal);
            resp.sendRedirect(forward);
        }
    }


    @Override
    public void init() throws ServletException {
        log.info("Init {}", this.getClass().getSimpleName());
        service = new MealServiceImpl(InMemoryMealRepositoryImpl.getInstance());

        getServletContext().setAttribute("formatter", DateTimeUtil.getDateTimeFormatter());
    }

    /*
    Даный метод выполняет построение экземпляра класса Meal из запроса.
    Во время построения выполняется валидация добавляемых полей.
    Результат валидации возвращается в переданный список validErr
    В случае наличия ошибок список будет не пуст.
     */
    private Meal validateAndGetMealFromRequest(List<String> validationR, HttpServletRequest req){
        return new Meal (
                new IdValidator().validate(req.getParameter("id"),validationR),
                new DateTimeValidator().validate(req.getParameter("dateTime"),validationR),
                new DescriptionValidator().validate(req.getParameter("description"),validationR),
                new CaloriesValidator().validate(req.getParameter("calories"), validationR)
        );
    }
}
