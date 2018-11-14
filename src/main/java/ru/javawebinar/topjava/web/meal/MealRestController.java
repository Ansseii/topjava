package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractController;

@Controller
public class MealRestController extends AbstractController {
    public MealRestController(MealService service) {
        super(service);
    }
}