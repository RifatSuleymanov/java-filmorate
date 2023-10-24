package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    @Autowired
    InMemoryFilmStorage inMemoryFilmStorage;
    @Autowired
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    FilmStorage filmStorage;

    User user;
    Film film;

    public Film addLike(Long userId, Long filmId) {
        if (inMemoryUserStorage.findById(userId).isPresent()) {
            user = inMemoryUserStorage.findById(userId).get();
        } else {
            throw new FilmNotFoundException("Фильм с такой id не существует");
        }
        if (inMemoryFilmStorage.findById(filmId).isPresent()) {
            film = inMemoryFilmStorage.findById(filmId).get();
        } else {
            throw new FilmNotFoundException("Фильм с такой id не существует");
        }
        film.getLikes().add(userId);
        return film;
    }

    public Film deleteLike(Long userId, Long filmId) {
        if (inMemoryUserStorage.findById(userId).isPresent()) {
            user = inMemoryUserStorage.findById(userId).get();
        } else {
            throw new FilmNotFoundException("Фильм с такой id не существует");
        }
        if (inMemoryFilmStorage.findById(filmId).isPresent()) {
            film = inMemoryFilmStorage.findById(filmId).get();
        } else {
            throw new FilmNotFoundException("Фильм с такой id не существует");
        }
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> tenPopularFilmByLike(Integer count) {
        log.info("Сортируем по количеству лайков");
        return filmStorage
                .getAll()
                .stream()
                .sorted((f0, f1) -> ((f1.getLikes().size()) - (f0.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());

    }
}