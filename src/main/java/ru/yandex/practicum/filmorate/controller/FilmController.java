package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private InMemoryFilmStorage inMemoryFilmStorage;
    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Film> returnAllFilms() {
        return inMemoryFilmStorage.getAll();
    }

    @PostMapping
    public Film addNewFilm(@RequestBody @Valid Film film) {
        return inMemoryFilmStorage.create(film);
    }

    @PutMapping()
    public Film updateFilm(@RequestBody @Valid Film film) {
        return inMemoryFilmStorage.update(film);
    }


    @GetMapping("/{id}")
    public Optional<Film> returnUsersById(@RequestBody @Valid @PathVariable("id") Long id) {
        return inMemoryFilmStorage.findById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@RequestBody @Valid @PathVariable("id") Long filmId, @PathVariable("userId") Long userId) {
        return filmService.addLike(userId, filmId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@RequestBody @Valid @PathVariable("id") Long filmId, @PathVariable("userId") Long userId) {
        return filmService.deleteLike(userId, filmId);
    }

    @GetMapping("/popular")
    public List<Film> tenPopularFilmByLike(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.tenPopularFilmByLike(count);
    }

}