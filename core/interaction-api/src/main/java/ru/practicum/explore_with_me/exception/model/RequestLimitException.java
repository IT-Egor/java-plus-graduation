package ru.practicum.explore_with_me.exception.model;

public class RequestLimitException extends RuntimeException {
    public RequestLimitException(String message) {
        super(message);
    }
}
