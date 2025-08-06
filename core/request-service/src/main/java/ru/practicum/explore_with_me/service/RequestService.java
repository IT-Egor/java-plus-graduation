package ru.practicum.explore_with_me.service;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explore_with_me.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.explore_with_me.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.explore_with_me.dto.request.RequestDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RequestService {
    Collection<RequestDto> getAllUserRequest(@PathVariable Long userId);

    RequestDto createRequest(@PathVariable Long userId, @RequestParam Long eventId);

    RequestDto cancelRequest(@PathVariable Long userId, @RequestParam Long requestId);

    Collection<RequestDto> getRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequest(Long userId, Long eventId, @Valid EventRequestStatusUpdateRequest updateRequest);

    Map<Long, List<RequestDto>> getConfirmedRequests(List<Long> eventIds);
}
