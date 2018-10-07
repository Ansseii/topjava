package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryStorageUtil {

    private static final List<Meal> MEALS = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(1,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(2,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(3,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(4,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(6,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    private static final LocalTime startTime = LocalTime.MIN;

    private static final LocalTime endTime = LocalTime.MAX;

    private static final int calories = 2000;

    public static LocalTime getStartTime() {
        return startTime;
    }

    public static LocalTime getEndTime() {
        return endTime;
    }

    public static int getCalories() {
        return calories;
    }

    public static List<Meal> getMeals() {
        return MEALS;
    }
}
