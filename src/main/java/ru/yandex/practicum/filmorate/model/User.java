package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    @PositiveOrZero
    private Long id;
    @NotBlank(message = "Не указан адрес электронной почты.")
    @Email(message = "Некорректный адрес электронной почты.")
    private String email;
    @NotBlank(message = "Не указан логин")
    @Pattern(regexp = "\\S+", message = "Логин содержит пробелы")
    private String login;
    private String name;
    @NotNull(message = "Не указана дата рождения")
    @PastOrPresent(message = "Некорректная дата рождения")
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();
}