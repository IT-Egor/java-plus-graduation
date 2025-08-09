package ru.practicum.explore_with_me.dto.compilations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.dto.event.EventShortDto;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CompilationResponse {
    Long id;
    String title;
    Boolean pinned;
    Set<EventShortDto> events;
}
