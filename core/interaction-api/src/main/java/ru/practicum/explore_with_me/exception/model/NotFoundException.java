package ru.practicum.explore_with_me.exception.model;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
