package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.Tuple;
import com.example.socialnetwork.repo.FriendshipRepoDB;
import com.example.socialnetwork.repo.UserRepoDB;

public class ServiceFriendship {
    private UserRepoDB userRepoDB;
    private FriendshipRepoDB friendshipRepoDB;

    public ServiceFriendship(UserRepoDB userRepoDB, FriendshipRepoDB friendshipRepoDB) {
        this.userRepoDB = userRepoDB;
        this.friendshipRepoDB = friendshipRepoDB;
    }

    public Iterable<Friendship> getAllFriendships() {
        return friendshipRepoDB.findAll();
    }

    public void removeFriendship(Long id1, Long id2) {
        if (id1 < id2) friendshipRepoDB.delete(new Tuple<>(id1, id2));
        else friendshipRepoDB.delete(new Tuple<>(id2, id1));
    }

    public int statusUsers(Long id1, Long id2) {
        if (friendshipRepoDB.findOneById(new Tuple<>(id1, id2)) == null && friendshipRepoDB.findOneById(new Tuple<>(id2, id1)) == null)
            return -1;
        if (id1 < id2) return friendshipRepoDB.findOneById(new Tuple<>(id1, id2)).getStatus();
        return friendshipRepoDB.findOneById(new Tuple<>(id2, id1)).getStatus();
    }

    public void updateStatus(Long id1, Long id2) {

        if (id1 < id2) {
            Friendship f = friendshipRepoDB.findOneById(new Tuple<Long, Long>(id1, id2));
            f.setStatus(1);
            friendshipRepoDB.update(friendshipRepoDB.findOneById(new Tuple<Long, Long>(id1, id2)), f);
            return;
        }

        Friendship f = friendshipRepoDB.findOneById(new Tuple<Long, Long>(id2, id1));
        f.setStatus(1);
        friendshipRepoDB.update(friendshipRepoDB.findOneById(new Tuple<Long, Long>(id2, id1)), f);

    }

    public Long requestFrom(Long id1, Long id2) {
        if (id1 < id2) return friendshipRepoDB.findOneById(new Tuple<>(id1, id2)).getFrom();
        return friendshipRepoDB.findOneById(new Tuple<>(id2, id1)).getFrom();

    }

    public void addFriendship(Friendship friendship) {
        friendshipRepoDB.save(friendship);
    }
}
