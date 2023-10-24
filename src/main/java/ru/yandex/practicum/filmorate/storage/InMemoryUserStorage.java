package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{

    private static Long idCounter = 0L;

    private final HashMap<Long, User> users;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }

    @Override
    public User save(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Сохраняем пользователя {} в список", user);
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
        if(users.containsKey(id)){
            log.info("Находим пользователя {} под id ", id);
            return Optional.ofNullable(users.get(id));
        }else {
            throw new UserNotFoundException("Пользователя по id не существует");
        }
    }

    @Override
    public List<User> getAll() {
        log.info("Выводим список пользоваетлей");
        return new ArrayList<>(users.values());
    }
}