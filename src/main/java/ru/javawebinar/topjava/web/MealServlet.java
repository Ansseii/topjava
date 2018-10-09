package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.storage.MealDao;
import ru.javawebinar.topjava.storage.StorageInMemoryImpl;
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
    private MealDao mealDao;
    private DateTimeFormatter formatter;

    @Override
    public void init() throws ServletException {
        super.init();
        mealDao = new StorageInMemoryImpl();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String id = req.getParameter("id");
        final String action = req.getParameter("action");
        log.debug("redirect to meals");
        Meal meal;
        switch (action == null ? "view" : action) {
            case "add":
                meal = MealsUtil.EMPTY;
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
                break;
            case "update":
                meal = mealDao.getById(Integer.parseInt(id));
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
                break;
            case "delete":
                mealDao.delete(Integer.parseInt(id));
                resp.sendRedirect("meals");
                return;
            case "view":
            default:
                final List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalDateTime.MIN.toLocalTime(),
                        LocalDateTime.MAX.toLocalTime(), 2000);
                req.setAttribute("meals", list);
                req.getRequestDispatcher("meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String id = req.getParameter("id");
        final String desc = req.getParameter("description");
        final String dateTime = req.getParameter("time");
        final int calories = Integer.parseInt(req.getParameter("calories"));

        try {
            LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
            Meal meal = new Meal(Integer.parseInt(id), formatDateTime, desc, calories);
            if (Integer.parseInt(id) > 0) {
                mealDao.update(meal);
                log.debug("updated");
            } else {
                mealDao.create(meal);
                log.debug("added");
            }
        } catch (DateTimeParseException e) {
            log.debug("DateTime parse exception :" + e);
        }

        final List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalDateTime.MIN.toLocalTime(),
                LocalDateTime.MAX.toLocalTime(), 2000);
        req.setAttribute("meals", list);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}

