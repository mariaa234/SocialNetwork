package com.example.socialnetwork.validators;


import com.example.socialnetwork.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity.getUser1() == entity.getUser2()) throw new ValidationException("Invalid friendship!");
    }
}
