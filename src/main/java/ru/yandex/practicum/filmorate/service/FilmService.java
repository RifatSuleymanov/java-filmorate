package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FilmService implements ImplService<Film> {

    private final static LocalDate START_RELEASE_DATE = LocalDate.of(1895,12,28);

    private static Long idCounter = 0L;

    private final HashMap<Long, Film> films;

    public FilmService() {
        films = new HashMap<>();
    }

    @Override
    public Film save(Film film) {
        films.put(film.getId(), film);
        return film;
    }
    public void validate(Film data){
        if(data.getReleaseDate().isBefore(START_RELEASE_DATE)){
            throw new ValidationException("Дата релиза некоректно");
        }
    }

    @Override
    public Film create(Film film) {
        validate(film);
        film.setId(++idCounter);
        log.info("Добавлен новый фильм: {}", film);
        return save(film);
    }

    @Override
    public Optional<Film> findById(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film update(Film film) {
        Film updateFilm;
        if (findById(film.getId()).isPresent()) {
            updateFilm = save(film);
            log.info("Данные фильма изменены: {}", updateFilm);
        } else {
            throw new FilmNotFoundException("Фильм с id " + film.getId() + " не найден.");
        }
        validate(film);
        return updateFilm;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }
}
