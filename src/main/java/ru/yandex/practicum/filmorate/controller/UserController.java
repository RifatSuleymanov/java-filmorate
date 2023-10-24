package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> returnAllUsers() {
        return inMemoryUserStorage.getAll();
    }

    @PostMapping
    public User addNewUser(@RequestBody @Valid User user) {
        return inMemoryUserStorage.create(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody @Valid User user) {
        return inMemoryUserStorage.update(user);
    }

    @GetMapping("/{id}")
    public Optional<User> returnUsersById(@RequestBody @Valid @PathVariable("id") Long id) {
        return inMemoryUserStorage.findById(id);
    }

    @PutMapping("/{id}/friends/{friendsId}")
    public User addFriends(@RequestBody @Valid @PathVariable("id") Long userId, @PathVariable("friendsId") Long friendsId) {
        return userService.addFriend(userId, friendsId);
    }

    @DeleteMapping("/{id}/friends/{friendsId}")
    public User deleteFriends(@RequestBody @Valid @PathVariable("id") Long userId, @PathVariable("friendsId") Long friendsId) {
        return userService.removeFriend(userId, friendsId);
    }

    @GetMapping("/{id}/friends")
    public List<User> returnAllFriendsUsers(@RequestBody @Valid @PathVariable("id") Long userId) {
        return userService.getUsersFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> returnMutualFriends(@RequestBody @Valid @PathVariable("id") Long userId, @PathVariable Long otherId) {
        return userService.getCommonFriends(userId, otherId);
    }
}