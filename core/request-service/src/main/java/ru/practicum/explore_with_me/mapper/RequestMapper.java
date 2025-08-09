package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.dto.request.RequestDto;
import ru.practicum.explore_with_me.model.Request;

@Mapper
public interface RequestMapper {
    @Mapping(target = "event", source = "eventId")
    @Mapping(target = "requester", source = "requesterId")
    RequestDto toRequestDto(Request request);
}
