package ru.practicum.explore_with_me.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.explore_with_me.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.explore_with_me.dto.request.RequestDto;
import ru.practicum.explore_with_me.service.RequestService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateEventRequestController {
    final RequestService requestService;

    @GetMapping
    public Collection<RequestDto> getRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return requestService.getRequests(userId, eventId);
    }

    @PatchMapping
    public EventRequestStatusUpdateResult updateRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @Valid @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return requestService.updateRequest(userId, eventId, updateRequest);
    }
}
