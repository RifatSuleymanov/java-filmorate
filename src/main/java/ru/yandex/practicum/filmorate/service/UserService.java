package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements ImplService<User> {

    private static Long idCounter = 0L;

    private final HashMap<Long, User> users;

    public UserService() {
        users = new HashMap<>();
    }

    @Override
    public User save(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User create(User user) {
        user.setId(++idCounter);
        log.info("Добавлен новый пользователь: {}", user);
        return save(user);
    }

    @Override
    public User update(User user) {
        User updateUser;
        if (findById(user.getId()).isPresent()) {
            updateUser = save(user);
            log.info("Данные пользователя изменены: {}", updateUser);
        } else {
            throw new UserNotFoundException("Пользователь с id " + user.getId() + " не найден.");
        }
        return updateUser;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
}