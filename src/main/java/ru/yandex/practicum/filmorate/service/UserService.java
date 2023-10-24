package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserStorage userStorage;

    public User addFriend(Long userId, Long friendId) {
        User user = getUserFromRepositoryOrThrowException(userId);
        User friend = getUserFromRepositoryOrThrowException(friendId);
        if (user.getFriends().contains(friendId)) {
            throw new UserNotFoundException(String
                    .format("Пользователи id: %d и id: %d уже являются друзьями.", userId, friendId));
        } else {
            user.getFriends().add(friendId);
            user = userStorage.save(user);
            friend.getFriends().add(userId);
            userStorage.save(friend);
            log.info("Пользователь с id {} добавил в друзья пользователя с id {}.", userId, friendId);
            return user;
        }
    }

    public User removeFriend(Long userId, Long friendId) {
        User user = getUserFromRepositoryOrThrowException(userId);
        User friend = getUserFromRepositoryOrThrowException(friendId);
        if (user.getFriends().contains(friendId) &&
                friend.getFriends().contains(userId)) {
            user.getFriends().remove(friendId);
            user = userStorage.save(user);
            friend.getFriends().remove(userId);
            userStorage.save(friend);
        } else {
            throw new UserNotFoundException(String
                    .format("Пользователи id: %d и id: %d не являются друзьями.", userId, friendId));
        }
        log.info("Пользователь с id {} удалил из друзей пользователя с id {}.", userId, friendId);
        return user;
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = getUserFromRepositoryOrThrowException(userId);
        User friend = getUserFromRepositoryOrThrowException(friendId);
        log.info("Запрошен список общих друзей пользователя с id {} и пользователя с id {}.", userId, friendId);
        return user
                .getFriends()
                .stream()
                .filter(friend.getFriends()::contains)
                .map(this::getUserFromRepositoryOrThrowException)
                .collect(Collectors.toList());
    }

    public List<User> getUsersFriends(Long userId) {
        User user = getUserFromRepositoryOrThrowException(userId);
        log.info("Запрошен список друзей пользователя с id: {}", userId);
        return user
                .getFriends()
                .stream()
                .map(this::getUserFromRepositoryOrThrowException)
                .collect(Collectors.toList());
    }

    private User getUserFromRepositoryOrThrowException(Long id) {
        return userStorage
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден."));
    }
}