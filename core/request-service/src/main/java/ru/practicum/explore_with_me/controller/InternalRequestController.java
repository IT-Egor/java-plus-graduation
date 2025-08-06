package ru.practicum.explore_with_me.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore_with_me.dto.request.RequestDto;
import ru.practicum.explore_with_me.feign.RequestFeign;
import ru.practicum.explore_with_me.service.RequestService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternalRequestController implements RequestFeign {
    final RequestService requestService;

    @Override
    public Map<Long, List<RequestDto>> getConfirmedRequests(List<Long> eventIds) {
        return requestService.getConfirmedRequests(eventIds);
    }
}
