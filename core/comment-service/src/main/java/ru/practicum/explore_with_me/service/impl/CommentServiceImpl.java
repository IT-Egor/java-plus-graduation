package ru.practicum.explore_with_me.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dao.CommentRepository;
import ru.practicum.explore_with_me.dto.comment.CommentResponse;
import ru.practicum.explore_with_me.dto.comment.MergeCommentRequest;
import ru.practicum.explore_with_me.dto.event.EventFullDto;
import ru.practicum.explore_with_me.enums.event.EventState;
import ru.practicum.explore_with_me.exception.model.NotFoundException;
import ru.practicum.explore_with_me.exception.model.PublicationException;
import ru.practicum.explore_with_me.feign.EventFeign;
import ru.practicum.explore_with_me.feign.UserFeign;
import ru.practicum.explore_with_me.mapper.CommentMapper;
import ru.practicum.explore_with_me.model.Comment;
import ru.practicum.explore_with_me.service.CommentService;

import java.util.Collection;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserFeign userFeign;
    private final EventFeign eventFeign;

    @Override
    public CommentResponse createComment(MergeCommentRequest mergeCommentRequest, Long userId) {
        userFeign.getUserById(userId);
        EventFullDto event = eventFeign.findEventById(mergeCommentRequest.getEventId());

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new PublicationException("Event must be published");
        }

        Comment comment = commentMapper.requestToComment(mergeCommentRequest, event.getId(), userId);
        CommentResponse response = commentMapper.commentToResponse(commentRepository.save(comment));
        log.info("Comment id={} was created by user id={}", response.getId(), response.getAuthor().getId());
        return response;
    }

    @Override
    public void deleteCommentByIdAndAuthor(Long commentId, Long userId) {
        if (commentRepository.deleteCommentByIdAndAuthorId(commentId, userId) != 0) {
            log.info("Comment with id={} was deleted by user id={}", commentId, userId);
        } else {
            throw new NotFoundException(String.format("Comment with id=%d by author id=%d was not found", commentId, userId));
        }
    }

    @Override
    public void deleteCommentById(Long commentId) {
        if (commentRepository.deleteCommentById(commentId) != 0) {
            log.info("Comment with id={} was deleted", commentId);
        } else {
            throw new NotFoundException(String.format("Comment with id=%d was not found", commentId));
        }
    }

    @Override
    public CommentResponse updateCommentByIdAndAuthorId(Long commentId, Long userId, MergeCommentRequest request) {
        Comment oldComment = commentRepository.findByIdAndAuthorId(commentId, userId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%d by author id=%d was not found", commentId, userId)));

        if (!oldComment.getEventId().equals(request.getEventId())) {
            throw new DataIntegrityViolationException("Event Id not correct");
        }

        commentMapper.updateComment(
                request,
                eventFeign.findEventById(request.getEventId()).getId(),
                oldComment);

        CommentResponse response = commentMapper.commentToResponse(commentRepository.save(oldComment));
        log.info("Comment id={} was updated by user id={}", response.getId(), response.getAuthor().getId());
        return response;
    }

    @Override
    public CommentResponse updateCommentById(Long commentId, MergeCommentRequest mergeCommentRequest) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%d was not found", commentId)));

        if (!oldComment.getEventId().equals(mergeCommentRequest.getEventId())) {
            throw new DataIntegrityViolationException("Event Id not correct");
        }

        commentMapper.updateComment(
                mergeCommentRequest,
                eventFeign.findEventById(mergeCommentRequest.getEventId()).getId(),
                oldComment);

        CommentResponse response = commentMapper.commentToResponse(commentRepository.save(oldComment));
        log.info("Comment id={} was updated", response.getId());
        return response;
    }

    @Override
    public Collection<CommentResponse> getAllCommentsByUser(Long userId, Integer from, Integer size) {
        log.info("Get all comments for user id={}", userId);
        return commentRepository.findAllByAuthorIdOrderByPublishedOnDesc(userId, createPageable(from, size))
                .stream()
                .map(commentMapper::commentToResponse)
                .toList();
    }

    @Override
    public Collection<CommentResponse> getAllCommentsByEvent(Long eventId, Integer from, Integer size) {
        log.info("Get all comments for event id={}", eventId);
        return commentRepository.findAllByEventIdOrderByPublishedOnDesc(eventId, createPageable(from, size))
                .stream()
                .map(commentMapper::commentToResponse)
                .toList();
    }

    @Override
    public Collection<CommentResponse> getAllCommentsByUserAndEvent(Long userId, Long eventId, Integer from, Integer size) {
        log.info("Get all comments for event id={} and user id={}", eventId, userId);
        return commentRepository.findAllByAuthorIdAndEventIdOrderByPublishedOnDesc(userId, eventId, createPageable(from, size))
                .stream()
                .map(commentMapper::commentToResponse)
                .toList();
    }

    @Override
    public CommentResponse getCommentById(Long commentId) {
        log.info("Get comment with id={}", commentId);
        return commentMapper.commentToResponse(commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%d was not found", commentId))));
    }

    private Pageable createPageable(Integer from, Integer size) {
        int pageNumber = from / size;
        return PageRequest.of(pageNumber, size);
    }
}
