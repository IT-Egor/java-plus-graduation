package ru.practicum.explore_with_me.exception.model;

public class DuplicateRequestException extends RuntimeException {
    public DuplicateRequestException(String message) {
        super(message);
    }
}
