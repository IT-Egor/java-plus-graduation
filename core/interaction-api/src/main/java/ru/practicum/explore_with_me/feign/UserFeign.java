package ru.practicum.explore_with_me.feign;

import feign.FeignException;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.explore_with_me.dto.user.UserResponse;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "user-service", path = "/admin/users")
public interface UserFeign {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Collection<UserResponse> getUsers(@RequestParam(required = false) List<Long> ids,
                                      @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                      @Positive @RequestParam(defaultValue = "10") Integer size) throws FeignException;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse getUserById(@PathVariable Long userId) throws FeignException;
}
