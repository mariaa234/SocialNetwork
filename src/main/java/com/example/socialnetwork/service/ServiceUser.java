package com.example.socialnetwork.service;

import com.example.socialnetwork.repo.UserRepoDB;

public class ServiceUser {
    private UserRepoDB userRepoDB;

    public ServiceUser(UserRepoDB userRepoDB) {
        this.userRepoDB = userRepoDB;
    }
}
