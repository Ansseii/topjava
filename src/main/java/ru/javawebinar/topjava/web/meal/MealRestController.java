package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    private final int userId;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
        userId = SecurityUtil.authUserId();
    }

    public Meal create(Meal meal) {
        log.info("create {} for user with ID {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete meal {} for user with ID {}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get meal {} for user with ID {}", id, userId);
        return service.get(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} for user with ID {}", meal, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll for user with ID {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LocalDate startD = startDate == null ? LocalDate.MIN : startDate;
        LocalDate endD = endDate == null ? LocalDate.MAX : endDate;
        LocalTime startT = startTime == null ? LocalTime.MIN : startTime;
        LocalTime endT = endTime == null ? LocalTime.MAX : endTime;

        return MealsUtil.getFilteredWithExceeded(service.getFiltered(userId, startD, endD),
                SecurityUtil.authUserCaloriesPerDay(), startT, endT);
    }
}