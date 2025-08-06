package ru.practicum.explore_with_me.feign;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.explore_with_me.dto.event.EventFullDto;

@FeignClient(name = "main-service", path = "/internal/events")
public interface EventFeign {
    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto findEventById(@PathVariable Long eventId) throws FeignException;

    @GetMapping("/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto getByUserIdAndEventId(@PathVariable Long userId, @PathVariable Long eventId) throws FeignException;
}
