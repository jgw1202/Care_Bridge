package org.example.carebridge.domain.like.repository;

import org.example.carebridge.domain.comment.entity.Comment;
import org.example.carebridge.domain.like.entity.Like;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    default Like findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ExceptionType.LIKE_NOT_FOUND));
    }
}
