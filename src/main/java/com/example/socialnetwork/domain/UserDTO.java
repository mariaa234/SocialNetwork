package com.example.socialnetwork.domain;

import java.util.Objects;

public class UserDTO {
    private String name;

    public UserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
