package org.example.carebridge.domain.comment.repository;

import org.example.carebridge.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 댓글을 찾을 수 없습니다."));
    }
}
