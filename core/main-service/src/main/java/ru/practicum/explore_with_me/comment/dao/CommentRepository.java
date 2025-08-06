package ru.practicum.explore_with_me.comment.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.comment.model.Comment;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    long deleteCommentByIdAndAuthorId(Long id, Long authorId);

    long deleteCommentById(Long commentId);

    Page<Comment> findAllByAuthorIdOrderByPublishedOnDesc(Long userId, Pageable pageable);

    Page<Comment> findAllByEvent_IdOrderByPublishedOnDesc(Long eventId, Pageable pageable);

    Optional<Comment> findByIdAndAuthorId(Long commentId, Long authorId);

    Page<Comment> findAllByAuthorIdAndEvent_IdOrderByPublishedOnDesc(Long userId, Long eventId, Pageable pageable);
}
