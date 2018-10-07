package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MemoryStorageUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements ICrud {

    private AtomicInteger counter = new AtomicInteger(MemoryStorageUtil.getMeals().size());
    private static List<Meal> list = MemoryStorageUtil.getMeals();

    @Override
    public void create(final Meal meal) {
        meal.setId(counter.incrementAndGet());
        getAllMeals().add(meal);
    }

    @Override
    public void update(final Meal meal) {
        getAllMeals().add(meal.getId(), meal);
    }

    @Override
    public void delete(final int id) {
        getAllMeals().remove(getMealById(id));
    }

    @Override
    public Meal getMealById(final int id) {
        for (Meal meal : getAllMeals()) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    public List<Meal> getAllMeals() {
        return list;
    }
}
