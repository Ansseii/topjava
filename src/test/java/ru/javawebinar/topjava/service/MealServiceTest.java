package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.web.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal actual = mealService.get(MEAL_ID + 2, USER_ID);
        Assertions.assertThat(actual).isEqualToComparingFieldByField(MEAL3);
    }

    @Test(expected = NotFoundException.class)
    public void getIfNotFound() {
        mealService.get(MEAL_ID + 2, ADMIN_ID);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        Assertions.assertThat(mealService.getAll(USER_ID))
                .usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));
    }

    @Test(expected = NotFoundException.class)
    public void deleteIfNotFound() {
        mealService.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDate() {
        Assertions.assertThat(mealService.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID))
                .usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void getAll() {
        Assertions.assertThat(mealService.getAll(USER_ID))
                .usingFieldByFieldElementComparator().isEqualTo(MEAL_LIST);
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL_ID, MEAL1.getDateTime(), "Обновленный завтрак", 200);
        mealService.update(meal, USER_ID);
        Assertions.assertThat(mealService.get(MEAL_ID, USER_ID)).isEqualToComparingFieldByField(meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateIfNotFound() {
        Meal meal = new Meal(MEAL_ID, MEAL1.getDateTime(), "Обновленный завтрак", 200);
        mealService.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = new Meal(null, LocalDateTime.of(2018, 12, 25, 15, 30),
                "Новый обед", 300);
        mealService.create(meal, USER_ID);
        Assertions.assertThat(mealService.getAll(USER_ID))
                .usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }
}