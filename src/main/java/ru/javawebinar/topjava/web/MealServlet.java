package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.storage.ICrud;
import ru.javawebinar.topjava.storage.MealDao;
import ru.javawebinar.topjava.util.MemoryStorageUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private ICrud dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        final String id = req.getParameter("id");
        final String action = req.getParameter("action");
        final List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), MemoryStorageUtil.getStartTime(),
                MemoryStorageUtil.getEndTime(), MemoryStorageUtil.getCalories());
        if (action == null) {
            req.setAttribute("meals", list);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        Meal meal;
        switch (action) {
            case "add":
                meal = Meal.EMPTY;
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
                break;
            case "update":
                meal = dao.getMealById(Integer.parseInt(id));
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
                break;
            case "delete":
                dao.delete(Integer.parseInt(id));
                resp.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("meals", list);
        log.debug("redirect to meals now");
        req.getRequestDispatcher("meals.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String id = req.getParameter("id");

        Meal meal = Meal.EMPTY;
        meal.setDescription(req.getParameter("description"));

        final String dateTime = req.getParameter("time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try {
            LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
            meal.setDateTime(formatDateTime);
        } catch (DateTimeParseException e) {
            log.debug("DateTime parse exception :" + e);
        }

        meal.setCalories(Integer.parseInt(req.getParameter("calories")));

        if (create(Integer.parseInt(id))) {
            dao.update(meal);
            log.debug("updated");
        } else {
            dao.create(meal);
            log.debug("added");
        }

        final List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), MemoryStorageUtil.getStartTime(),
                MemoryStorageUtil.getEndTime(), MemoryStorageUtil.getCalories());
        req.setAttribute("meals", list);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    private boolean create(final int id) {
        return id > 0;
    }
}

