// CommentRepository.java
package org.example.carebridge.domain.comment.repository;

import org.example.carebridge.domain.comment.entity.Comment;
import org.example.carebridge.global.exception.NotFoundException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 댓글 리포지토리 인터페이스.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * ID로 댓글을 찾고, 없으면 예외를 던집니다.
     *
     * @param id 댓글 ID
     * @return 댓글 엔티티
     */
    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ExceptionType.COMMENT_NOT_FOUND));
    }
}