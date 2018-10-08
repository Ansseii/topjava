package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageInMemoryImpl implements Dao {

    private AtomicInteger counter;

    private Map<Integer, Meal> map;

    public StorageInMemoryImpl() {
        map = new ConcurrentHashMap<>();
        for (Meal meal : MealsUtil.MEALS) {
            map.putIfAbsent(meal.getId(), meal);
        }
        counter = new AtomicInteger(map.size());
    }

    @Override
    public void create(final Meal meal) {
        meal.setId(counter.incrementAndGet());
        map.put(meal.getId(), meal);
    }

    @Override
    public void update(final Meal meal) {
        map.put(meal.getId(), meal);
    }

    @Override
    public void delete(final int id) {
        map.remove(id);
    }

    @Override
    public Meal getMealById(final int id) {
        return map.get(id);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new CopyOnWriteArrayList<>(map.values());
    }
}
