package ru.practicum.explore_with_me.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore_with_me.dto.event.EventFullDto;
import ru.practicum.explore_with_me.event.service.EventService;
import ru.practicum.explore_with_me.feign.EventFeign;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternalEventController implements EventFeign {
    final EventService eventService;

    @Override
    public EventFullDto findEventById(Long eventId) {
        return eventService.getEventById(eventId);
    }

    @Override
    public EventFullDto getByUserIdAndEventId(Long userId, Long eventId) {
        return eventService.getEventById(userId, eventId);
    }
}