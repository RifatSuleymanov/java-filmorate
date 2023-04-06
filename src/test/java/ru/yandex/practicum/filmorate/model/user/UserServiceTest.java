package ru.yandex.practicum.filmorate.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.repository.UserRepositoryImpl;
import ru.yandex.practicum.filmorate.service.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    private UserRepository repository = new UserRepositoryImpl();
    @InjectMocks
    private UserServiceImpl service = new UserServiceImpl();
    private User user1;
    private User user2;

    @BeforeEach
    public void createUsers() {
        user1 = User.builder()
                .email("fc.chelsea11@yandex.ru")
                .login("Chelsea11")
                .name("Рифат")
                .birthday(LocalDate.of(1994, 8, 29))
                .build();
        user2 = User.builder()
                .email("aygul@yandex.ru")
                .login("zvezda")
                .name("Айгуль")
                .birthday(LocalDate.of(1997, 4, 8))
                .build();
    }

    @Test
    void shouldAddUserAndReturnIt() {
        given(repository.save(any(User.class))).willReturn(user1);

        User savedUser = service.create(user1);

        verify(repository).save(user1);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isEqualTo(user1);
    }

    @Test
    void shouldUpdateUserAndReturnIt() {
        given(repository.save(user1)).willReturn(user1);
        given(repository.findById(anyInt())).willReturn(Optional.of(user1));
        given(repository.save(user2)).willReturn(user2);

        User savedUser = service.create(user1);
        int id = savedUser.getId();
        user2.setId(id);
        User updatedUser = service.update(user2);

        verify(repository).save(user1);
        verify(repository).save(user2);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser).isEqualTo(user2);
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> users = List.of(user1, user2);
        given(repository.getAllUsers()).willReturn(users);

        List<User> allUsers = service.getAllUsers();

        verify(repository).getAllUsers();
        assertThat(allUsers).isNotNull();
        assertThat(allUsers.size()).isEqualTo(users.size());
        assertThat(allUsers).isEqualTo(users);
    }
}