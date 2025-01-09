package org.example.carebridge.domain.like.service;

import org.example.carebridge.domain.like.dto.LikeRequestDto;
import org.example.carebridge.domain.like.dto.LikeResponseDto;

public interface LikeService {
    LikeResponseDto createLike(Long boardId, Long userId);
    LikeResponseDto deleteLike(Long likeId, Long userId);
}
