package ru.practicum.explore_with_me.mapper;

import org.mapstruct.*;
import ru.practicum.explore_with_me.dto.comment.CommentResponse;
import ru.practicum.explore_with_me.dto.comment.MergeCommentRequest;
import ru.practicum.explore_with_me.model.Comment;

@Mapper
public interface CommentMapper {
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedOn", source = "commentRequest.publishedOn")
    Comment requestToComment(MergeCommentRequest commentRequest, Long eventId, Long authorId);

    CommentResponse commentToResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "publishedOn", source = "commentRequest.publishedOn")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateComment(MergeCommentRequest commentRequest, Long eventId, @MappingTarget Comment comment);
}
