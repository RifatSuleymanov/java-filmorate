package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.FilmRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {

    private static Long idCounter = 0L;
    @Autowired
    private FilmRepository repository = new FilmRepositoryImpl();
    @Override
    public List<Film> getAllFilms() {
        return repository.getAllFilms();
    }

    @Override
    public Film create(Film film) {
        film.setId(++idCounter);
        log.info("Добавлен новый фильм: {}", film);
        return repository.save(film);
    }

    @Override
    public Film update(Film film) {
        Film updatedFilm;
        if(!repository.findById(film.getId()).isPresent()) {
            throw new FilmNotFoundException("Фильм с id " + film.getId() + " не найден.");
        }
        updatedFilm = repository.save(film);
            log.info("Данные фильма изменены: {}", updatedFilm);
        return updatedFilm;
    }
}
