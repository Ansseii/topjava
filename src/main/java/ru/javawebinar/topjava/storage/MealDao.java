package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getAll();

    Meal getById(int id);

    Meal create(final Meal meal);

    Meal update(final Meal meal);

    void delete(final int id);

}
