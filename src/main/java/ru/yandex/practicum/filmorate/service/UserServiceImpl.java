package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.repository.UserRepositoryImpl;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static Long idCounter = 0L;

    @Autowired
    private UserRepository repository = new UserRepositoryImpl();

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    public User create(User user) {
        user.setId(++idCounter);
        log.info("Добавлен новый пользователь: {}", user);
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        User updatedUser;
        if (repository.findById(user.getId()).isPresent()) {
            updatedUser = repository.save(user);
            log.info("Данные пользователя изменены: {}", user);
        } else {
            throw new UserNotFoundException("Пользователь с id " + user.getId() + " не найден.");
        }
        return updatedUser;
    }
}
