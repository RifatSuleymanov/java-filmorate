package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Film> returnAllFilms() {
        return filmService.getAll();
    }

    @PostMapping
    public Film addNewFilm(@RequestBody @Valid Film film) {
        return filmService.create(film);
    }

    @PutMapping()
    public Film updateFilm(@RequestBody @Valid Film film) {
        return filmService.update(film);
    }


}
