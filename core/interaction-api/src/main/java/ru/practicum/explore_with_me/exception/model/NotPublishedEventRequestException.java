package ru.practicum.explore_with_me.exception.model;

public class NotPublishedEventRequestException extends RuntimeException {
    public NotPublishedEventRequestException(String message) {
        super(message);
    }
}
