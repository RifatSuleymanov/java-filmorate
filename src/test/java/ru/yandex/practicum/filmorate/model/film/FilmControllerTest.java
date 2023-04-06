package ru.yandex.practicum.filmorate.model.film;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.LocalDate;
import java.util.List;

@WebMvcTest(FilmController.class)

public class FilmControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    FilmService service;
    Film film1;
    Film film2;

    @BeforeEach
    public void createFilms() {
        film1 = Film.builder()
                .name("Легенда №17")
                .description("Фильм про русского хоккеиста")
                .releaseDate(LocalDate.of(2017, 12, 1))
                .duration(130)
                .build();
        film2 = Film.builder()
                .name("Достучаться до небес")
                .description("Фильм про двух друзей")
                .releaseDate(LocalDate.of(1994, 9, 23))
                .duration(160)
                .build();
    }

    @Test
    void shouldAddFilmAndReturnIt() throws Exception {
        when(service.create(any(Film.class))).thenReturn(film1);

        var mvcRequest = post("/films").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(film1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(mvcRequest)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Легенда №17")))
                .andExpect(jsonPath("$.description",
                        is("Фильм про русского хоккеиста")))
                .andExpect(jsonPath("$.releaseDate", is("2017-12-01")))
                .andExpect(jsonPath("$.duration", is(130)));
    }

    @Test
    void shouldUpdateFilmAndReturnIt() throws Exception {
        film2.setId(1L);
        when(service.update(any(Film.class))).thenReturn(film2);

        var mvcRequest = put("/films").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(film2))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(mvcRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Достучаться до небес")))
                .andExpect(jsonPath("$.description",
                        is("Фильм про двух друзей")))
                .andExpect(jsonPath("$.releaseDate", is("1994-09-23")))
                .andExpect(jsonPath("$.duration", is(160)));
    }

    @Test
    void shouldReturnAllFilms() throws Exception {
        film1.setId(1L);
        film2.setId(2L);
        when(service.getAllFilms()).thenReturn(List.of(film1, film2));

        var mvcRequest = get("/films").accept(MediaType.APPLICATION_JSON);

        mvc.perform(mvcRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(List.of(film1, film2))))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", contains(1, 2)))
                .andExpect(jsonPath("$[*].name", contains("Легенда №17", "Достучаться до небес")))
                .andExpect(jsonPath("$[*].description",
                        contains("Фильм про русского хоккеиста",
                                "Фильм про двух друзей")))
                .andExpect(jsonPath("$[*].releaseDate", contains("2017-12-01", "1994-09-23")))
                .andExpect(jsonPath("$[*].duration", contains(130, 160)));
    }

}
