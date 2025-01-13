package org.example.carebridge.domain.like.service;

import org.example.carebridge.domain.like.dto.LikeDeleteResponseDto;
import org.example.carebridge.domain.like.dto.LikeResponseDto;

public interface LikeService {
    LikeResponseDto createLike(Long boardId, Long userId);
    LikeDeleteResponseDto deleteLike(Long likeId, Long userId);
}
