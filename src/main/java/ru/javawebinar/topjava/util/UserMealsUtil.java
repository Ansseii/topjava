package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
//        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(final List<UserMeal> mealList, final LocalTime startTime,
                                                                   final LocalTime endTime, final int caloriesPerDay) {
        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(dateTime, meal.getDescription(),
                        meal.getCalories(), countCaloriesPerDay(mealList, caloriesPerDay, dateTime)));
            }
        }

        return list;
    }

    private static boolean countCaloriesPerDay(final List<UserMeal> mealList, final int caloriesPerDay, final LocalDateTime key) {
        Map<LocalDate, Integer> calories = new HashMap<>();
        for (UserMeal meal : mealList) {
            calories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        return caloriesPerDay < calories.get(key.toLocalDate());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(final List<UserMeal> mealList, final LocalTime startTime,
                                                                         final LocalTime endTime, final int caloriesPerDay) {
        Map<LocalDate, Integer> calories = mealList.stream()
                .collect(Collectors.groupingBy((date) -> date.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(u -> TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime))
                .map(user -> new UserMealWithExceed(user.getDateTime(), user.getDescription(),
                        user.getCalories(), calories.get(user.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}