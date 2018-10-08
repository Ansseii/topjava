package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    List<Meal> getAllMeals();

    Meal getMealById(int id);

    void create(final Meal meal);

    void update(final Meal meal);

    void delete(final int id);

}
