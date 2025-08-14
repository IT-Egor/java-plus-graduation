package ru.practicum.explore_with_me.feign;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explore_with_me.dto.request.RequestDto;

import java.util.List;
import java.util.Map;

@FeignClient(name = "request-service", path = "/internal/requests")
public interface RequestFeign {
    @GetMapping("/confirmed")
    Map<Long, List<RequestDto>> getConfirmedRequests(@RequestParam List<Long> eventIds) throws FeignException;

    @GetMapping("/{userId}/{eventId}")
    boolean isUserInEvent(@PathVariable Long userId, @PathVariable Long eventId) throws FeignException;
}
