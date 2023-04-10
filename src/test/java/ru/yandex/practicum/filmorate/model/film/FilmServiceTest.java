package ru.yandex.practicum.filmorate.model.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {
    @Mock
    private FilmRepository repository;
    @InjectMocks
    private FilmServiceImpl service;
    private Film film1;
    private Film film2;

    @BeforeEach
    public void createFilms() {
        film1 = Film.builder()
                .name("Легенда №17")
                .description("Фильм про русского хоккеиста")
                .releaseDate(LocalDate.of(2017, 12, 01))
                .duration(195)
                .build();
        film2 = Film.builder()
                .name("Достучаться до небес")
                .description("Фильм про двух друзей")
                .releaseDate(LocalDate.of(1994, 7, 15))
                .duration(142)
                .build();
    }

    @Test
    void shouldAddFilmAndReturnIt() {
        given(repository.save(any(Film.class))).willReturn(film1);

        Film film = service.create(film1);

        verify(repository).save(film1);
        assertThat(film).isNotNull();
        assertThat(film).isEqualTo(film1);
    }

    @Test
    void shouldUpdateFilmAndReturnIt() {
        given(repository.save(film1)).willReturn(film1);
        given(repository.findById(anyLong())).willReturn(Optional.of(film1));
        given(repository.save(film2)).willReturn(film2);

        Film film = service.create(film1);
        long id = film.getId();
        film2.setId(id);
        Film updatedFilm = service.update(film2);

        verify(repository).save(film1);
        verify(repository).save(film2);
        assertThat(updatedFilm).isNotNull();
        assertThat(updatedFilm).isEqualTo(film2);
    }

    @Test
    void shouldReturnAllFilms() {
        List<Film> films = List.of(film1, film2);
        given(repository.getAllFilms()).willReturn(films);

        List<Film> allFilms = service.getAllFilms();

        assertThat(allFilms).isNotNull();
        assertThat(allFilms.size()).isEqualTo(films.size());
        assertThat(allFilms).isEqualTo(films);
    }
}