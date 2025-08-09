package ru.practicum.explore_with_me.exception.model;

public class AlreadyPublishedException extends RuntimeException {
    public AlreadyPublishedException(String message) {
        super(message);
    }
}