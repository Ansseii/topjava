package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageInMemoryImpl implements MealDao {

    private AtomicInteger counter;

    private Map<Integer, Meal> map;

    public StorageInMemoryImpl() {
        map = new ConcurrentHashMap<>();
        fillMap(map);
        counter = new AtomicInteger(map.size());
    }

    @Override
    public Meal create(final Meal meal) {
        meal.setId(counter.incrementAndGet());
        map.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(final Meal meal) {
        map.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(final int id) {
        map.remove(id);
    }

    @Override
    public Meal getById(final int id) {
        return map.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    private void fillMap(final Map<Integer, Meal> map) {
        for (Meal meal : MealsUtil.MEALS) {
            map.putIfAbsent(meal.getId(), meal);
        }
    }
}
