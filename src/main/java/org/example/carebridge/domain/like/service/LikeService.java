package org.example.carebridge.domain.like.service;

import org.example.carebridge.domain.like.dto.LikeDeleteResponseDto;
import org.example.carebridge.domain.like.dto.LikeResponseDto;

/**
 * LikeService 인터페이스.
 * 좋아요(Like)와 관련된 비즈니스 로직을 정의하는 인터페이스.
 */
public interface LikeService {

    /**
     * 좋아요 생성 메서드.
     * 특정 게시판(Board)와 사용자(User) ID를 기반으로 좋아요를 생성.
     *
     * @param boardId 좋아요를 추가할 게시판의 ID
     * @param userId  좋아요를 추가하는 사용자의 ID
     * @return 생성된 좋아요 정보를 담은 LikeResponseDto
     */
    LikeResponseDto createLike(Long boardId, Long userId);

    /**
     * 좋아요 삭제 메서드.
     * 특정 좋아요 ID와 사용자 ID를 기반으로 좋아요를 삭제.
     *
     * @param likeId 삭제할 좋아요의 ID
     * @param userId 삭제 요청을 한 사용자의 ID
     * @return 삭제 결과 메시지를 담은 LikeDeleteResponseDto
     */
    LikeDeleteResponseDto deleteLike(Long likeId, Long userId);
}
