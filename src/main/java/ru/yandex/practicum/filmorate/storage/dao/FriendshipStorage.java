package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipStorage {
    Friendship save(Friendship friendship);

    Optional<Friendship> findFriendship(Friendship friendship);

    List<Long> findFriendsIdByUserId(long id);

    boolean isConfirmFriendship(Friendship friendship);

    void cancelFriendship(Friendship friendship);

    boolean isExist(Friendship friendship);

    boolean isConfirmed(Friendship friendship);

    void deleteAll();
}
