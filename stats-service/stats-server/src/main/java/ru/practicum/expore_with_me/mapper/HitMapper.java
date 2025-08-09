package ru.practicum.expore_with_me.mapper;

import ru.practicum.explore_with_me.dto.stats.HitRequest;
import org.mapstruct.Mapper;
import ru.practicum.expore_with_me.model.Hit;

@Mapper
public interface HitMapper {
    Hit requestToHit(HitRequest hitRequest);
}
