package ru.practicum.explore_with_me.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.event.EventFullDto;
import ru.practicum.explore_with_me.dto.event.EventShortDto;
import ru.practicum.explore_with_me.dto.event.GetAllEventsPublicParams;
import ru.practicum.explore_with_me.enums.event.SortType;
import ru.practicum.explore_with_me.event.service.EventService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventShortDto> getAllEventsPublic(
            @RequestParam(required = false) @Size(min = 1, max = 7000, message = "Description should be between 1 and 7000 characters long") String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortType sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest httpServletRequest) {
        return eventService.getAllEventsPublic(GetAllEventsPublicParams.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .httpServletRequest(httpServletRequest)
                .build());
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByIdPublic(@PathVariable Long eventId,
                                           @RequestHeader("X-EWM-USER-ID") Long userId) {
        return eventService.getEventByIdPublic(eventId, userId);
    }

    @GetMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getRecommendations(@RequestHeader("X-EWM-USER-ID") Long userId) {
        log.info("Get recommendations for user with id: {}", userId);
        return eventService.getRecommendations(userId);
    }

    @PutMapping("{eventId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeEvent(@RequestHeader("X-EWM-USER-ID") Long userId, @PathVariable Long eventId) {
        log.info("Like event with id: {} for user with id: {}", eventId, userId);
        eventService.likeEvent(userId, eventId);
    }
}
