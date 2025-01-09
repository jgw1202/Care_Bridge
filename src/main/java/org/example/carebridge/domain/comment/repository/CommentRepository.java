package org.example.carebridge.domain.comment.repository;

import org.example.carebridge.domain.comment.entity.Comment;
import org.example.carebridge.global.exception.CommentNotFoundException;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ExceptionType.COMMENT_NOT_FOUND));
    }

}
