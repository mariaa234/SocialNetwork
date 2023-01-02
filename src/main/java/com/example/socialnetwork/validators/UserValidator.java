package com.example.socialnetwork.validators;


import com.example.socialnetwork.domain.User;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ValidationException {
        if (user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty())
            throw new ValidationException("User invalid!");
        }
}