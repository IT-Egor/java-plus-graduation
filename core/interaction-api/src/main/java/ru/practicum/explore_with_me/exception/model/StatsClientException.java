package ru.practicum.explore_with_me.exception.model;

public class StatsClientException extends RuntimeException {
    public StatsClientException(Integer code, String message) {
        super("StatusCode = " + code + "\nMessage = " + message);
    }
}