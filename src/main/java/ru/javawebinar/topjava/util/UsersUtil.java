package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User("John", "john@gmail.com", "johnpass", Role.ROLE_USER),
            new User("Denis", "denis@gmail.com", "denpass", Role.ROLE_USER),
            new User("Jack", "jack@gmail.com", "jackpass", Role.ROLE_ADMIN),
            new User("Thomas", "thomas@gmail.com", "thomaspass", Role.ROLE_USER),
            new User("Sam", "sam@gmail.com", "sampass", Role.ROLE_ADMIN)
    );
}
