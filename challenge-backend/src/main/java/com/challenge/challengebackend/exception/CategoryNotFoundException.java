package com.challenge.challengebackend.exception;

public class CategoryNotFoundException extends CustomException {

    public CategoryNotFoundException(Long id) {
        super("Couldn't find a category with id " + id);
    }
}
