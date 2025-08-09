package ru.practicum.explore_with_me.dto.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.enums.event.SortType;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetAllEventsPublicParams {
    String text;
    Set<Long> categories;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    SortType sort;
    Integer from;
    Integer size;
    HttpServletRequest httpServletRequest;
}
