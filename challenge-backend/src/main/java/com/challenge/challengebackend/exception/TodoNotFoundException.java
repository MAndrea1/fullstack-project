package com.challenge.challengebackend.exception;

public class TodoNotFoundException extends CustomException {

    public TodoNotFoundException(Long id) {
        super("Couldn't find a TODO with id " + id);
    }
}
