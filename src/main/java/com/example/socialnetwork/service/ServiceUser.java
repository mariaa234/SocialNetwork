package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.repo.UserRepoDB;

public class ServiceUser {
    private final UserRepoDB userRepoDB;

    public ServiceUser(UserRepoDB userRepoDB) {
        this.userRepoDB = userRepoDB;
    }


    public User findUserByUsernameAndPassword(String username, String password) {

        Iterable<User> users = userRepoDB.findAll();
        for (User user : users)
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) return user;
        return null;
    }


    public User findOneUser(long user2) {
        Iterable<User> users = userRepoDB.findAll();
        for (User user : users)
            if (user.getId() == user2) return user;
        return null;

    }

    public User findUserByName(String lastname, String firstname) {
        Iterable<User> users = userRepoDB.findAll();
        for (User user : users)
            if (firstname.equals(user.getFirstname()) && lastname.equals(user.getLastname())) return user;
        return null;
    }

    public Long findByUsername(String username) {
        Iterable<User> users = userRepoDB.findAll();
        for (User user : users)
            if (username.equals(user.getUsername())) return user.getId();
        return -1L;
    }


    public void addUser(User user) {
        userRepoDB.save(user);
    }
}
