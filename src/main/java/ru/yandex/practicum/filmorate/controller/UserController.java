package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> returnAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public User addNewUser(@RequestBody @Valid User user){
        return userService.create(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody @Valid User user){
        return userService.update(user);
    }
}
