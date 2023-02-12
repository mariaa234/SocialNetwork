package com.example.socialnetwork.domain;

import java.util.Date;
import java.util.Objects;

public class User extends Entity<Long> {

    private String username;
    private String lastname;
    private String firstname;
    private String email;
    private String password;

    public User(String username, String lastname, String firstname, String email, String password) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastname, firstname, username, password);
    }
}
