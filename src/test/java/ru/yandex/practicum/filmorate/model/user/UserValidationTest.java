package ru.yandex.practicum.filmorate.model.user;

import ru.yandex.practicum.filmorate.model.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserValidationTest {
    private static Validator validator;
    private User user;

    @BeforeAll
    public static void getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void getValidUser() {
        user = User.builder()
                .email("fc.chelsea11@yandex.ru")
                .login("Chelsea11")
                .name("Рифат")
                .birthday(LocalDate.of(1994, 8, 29))
                .build();
    }

    @Test
    @DisplayName("Email without @")
    public void shouldConstraintViolationWhenEmailWithoutAt() {
        user.setEmail(user.getEmail().replace("@", ""));
        var violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Электронная почта не содержит «@».");
    }

    @Test
    @DisplayName("Email is empty")
    public void shouldConstraintViolationWhenEmailIsEmpty() {
        user.setEmail("");
        var violation = validator.validate(user);

        assertFalse(violation.isEmpty(), "Пустое значение поля «email».");
    }

    @Test
    @DisplayName("Login is empty")
    public void shouldConstraintViolationWhenLoginIsEmpty() {
        user.setLogin("");
        var violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Пустое значение поля «login».");
    }

    @Test
    @DisplayName("Login is null")
    public void shouldConstraintViolationWhenLoginIsNull() {
        user.setLogin(null);
        var violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Значение поля «login» — null.");
    }

    @Test
    @DisplayName("Login contains space")
    public void shouldConstraintViolationWhenLoginContainsSpace() {
        user.setLogin("Рифат Сулейманов");
        var violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Логин содержит пробел.");
    }

    @Test
    @DisplayName("User's birthday in the future")
    public void shouldConstraintViolationWhenBirthdayInTheFuture() {
        user.setBirthday(LocalDate.now().plusYears(1000));
        var violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Добро пожаловать.");
    }
}