package ru.yandex.practicum.filmorate.storage;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage{
    User create(User user);

    User update(User user);

    Optional<User> findById(Long id);

    List<User> getAll();

    User save(User user);
}