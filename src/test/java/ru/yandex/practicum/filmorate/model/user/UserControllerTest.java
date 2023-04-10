package ru.yandex.practicum.filmorate.model.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)

public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService service;
    private User user1;
    private User user2;

    @BeforeEach
    public void createUsers() {
        user1 = User.builder()
                .id(0L)
                .email("fc.chelsea11@yandex.ru")
                .login("Chelsea11")
                .name("Рифат")
                .birthday(LocalDate.of(1994, 8, 29))
                .build();
        user2 = User.builder()
                .id(1L)
                .email("aygul@yandex.ru")
                .login("zvezda")
                .name("Айгуль")
                .birthday(LocalDate.of(1997, 4, 8))
                .build();
    }

    @Test
    void shouldAddUserAndReturnIt() throws Exception {
        when(service.create(any(User.class))).thenReturn(user1);

        var mvcRequest = post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(mvcRequest)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is("fc.chelsea11@yandex.ru")))
                .andExpect(jsonPath("$.login", is("Chelsea11")))
                .andExpect(jsonPath("$.name", is("Рифат")))
                .andExpect(jsonPath("$.birthday", is("1994-08-29")));
    }

    @Test
    void shouldUpdateUserAndReturnIt() throws Exception {
        user2.setId(1L);
        when(service.update(any(User.class))).thenReturn(user2);

        var mvcRequest = put("/users").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user2))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(mvcRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("aygul@yandex.ru")))
                .andExpect(jsonPath("$.login", is("zvezda")))
                .andExpect(jsonPath("$.name", is("Айгуль")))
                .andExpect(jsonPath("$.birthday", is("1997-04-08")));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        user1.setId(1L);
        user2.setId(2L);
        when(service.getAllUsers()).thenReturn(List.of(user1, user2));

        var mvcRequest = get("/users").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(List.of(user1, user2)))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(mvcRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].email", contains("fc.chelsea11@yandex.ru", "aygul@yandex.ru")))
                .andExpect(jsonPath("$[*].login", contains("Chelsea11", "zvezda")))
                .andExpect(jsonPath("$[*].name", contains("Рифат", "Айгуль")))
                .andExpect(jsonPath("$[*].birthday", contains("1994-08-29", "1997-04-08")))
                .andExpect(jsonPath("$[*].id", contains(1, 2)));
    }
}