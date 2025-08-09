package ru.practicum.explore_with_me.exception.model;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }
}