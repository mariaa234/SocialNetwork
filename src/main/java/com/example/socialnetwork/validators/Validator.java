package com.example.socialnetwork.validators;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}