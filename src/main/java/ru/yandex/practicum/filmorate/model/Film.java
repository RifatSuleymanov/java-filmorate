package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @PositiveOrZero
    private Long id;
    @NotBlank(message = "Не указано название фильма.")
    private String name;
    @NotNull(message = "Не указано описание фильма.")
    @Size(min = 1, max = 200, message = "Максимальный размер описания фильма — 200 символов.")
    private String description;
    @NotNull(message = "Не указана дата выпуска фильма.")
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность фильма указана не верно.")
    @Positive
    private long duration;
    private Set<Long> likes = new HashSet<>();
}