package ru.practicum.explore_with_me.event.service;

import ru.practicum.explore_with_me.dto.event.*;

import java.util.Collection;
import java.util.List;

public interface EventService {
    Collection<EventShortDto> getAllEvents(Long userId, Integer from, Integer size);

    Collection<EventFullDto> getAllEventsAdmin(GetAllEventsAdminParams params);

    Collection<EventShortDto> getAllEventsPublic(GetAllEventsPublicParams params);

    EventFullDto patchEventById(Long eventId, AdminPatchEventDto adminPatchEventDto);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto getEventById(Long eventId);

    EventFullDto getEventByIdPublic(Long eventId, Long userId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateRequest);

    List<EventFullDto> getRecommendations(Long userId);

    void likeEvent(Long userId, Long eventId);
}