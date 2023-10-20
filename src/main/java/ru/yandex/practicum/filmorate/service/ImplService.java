package ru.yandex.practicum.filmorate.service;

import java.util.List;
import java.util.Optional;

public interface ImplService<T>{

    T create(T t);

    T update(T t);


    Optional<T> findById(Long id);

    List<T> getAll();

    T save(T t);


}
