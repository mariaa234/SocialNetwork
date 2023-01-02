package com.example.socialnetwork.service;

import com.example.socialnetwork.repo.FriendshipRepoDB;
import com.example.socialnetwork.repo.UserRepoDB;

public class ServiceFriendship {
    private UserRepoDB userRepoDB;
    private FriendshipRepoDB friendshipRepoDB;

    public ServiceFriendship(UserRepoDB userRepoDB, FriendshipRepoDB friendshipRepoDB) {
        this.userRepoDB = userRepoDB;
        this.friendshipRepoDB = friendshipRepoDB;
    }
}
