package org.example.carebridge.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.like.dto.LikeDeleteResponseDto;
import org.example.carebridge.domain.like.dto.LikeResponseDto;
import org.example.carebridge.domain.like.service.LikeService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 좋아요 기능의 REST 컨트롤러.
 * 사용자가 특정 게시판의 좋아요를 생성하거나 삭제할 수 있는 기능을 제공.
 */
@RestController
@RequiredArgsConstructor // 생성자를 통한 의존성 주입을 자동으로 생성
@RequestMapping("/api/boards/{boardId}/likes") // 모든 요청이 이 경로를 기반으로 처리
public class LikeController {

    private final LikeService likeService; // LikeService를 통해 비즈니스 로직을 처리

    /**
     * 특정 게시판에 좋아요를 생성하는 메서드.
     *
     * @param boardId 게시판의 ID
     * @param userDetailsImpl 인증된 사용자 정보를 제공하는 객체
     * @return 생성된 좋아요의 정보를 담은 LikeResponseDto와 HTTP 상태코드 200 반환
     */
    @PostMapping
    public ResponseEntity<LikeResponseDto> createLike(
            @PathVariable Long boardId, // URL 경로에서 게시판 ID 추출
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl // 인증된 사용자 정보
    ) {
        LikeResponseDto response = likeService.createLike(boardId, userDetailsImpl.getUser().getId());
        return ResponseEntity.ok(response); // HTTP 200 상태와 함께 LikeResponseDto 반환
    }

    /**
     * 특정 좋아요를 삭제하는 메서드.
     *
     * @param likeId 삭제할 좋아요의 ID
     * @param userDetailsImpl 인증된 사용자 정보를 제공하는 객체
     * @return 삭제 결과 메시지를 포함하는 LikeDeleteResponseDto와 HTTP 상태코드 200 반환
     */
    @DeleteMapping("/{likeId}")
    public ResponseEntity<LikeDeleteResponseDto> deleteLike(
            @PathVariable Long likeId, // URL 경로에서 좋아요 ID 추출
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl // 인증된 사용자 정보
    ) {
        LikeDeleteResponseDto responseDto = likeService.deleteLike(likeId, userDetailsImpl.getUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200 상태 설정
                .contentType(MediaType.APPLICATION_JSON) // 응답의 Content-Type을 JSON으로 명시
                .body(responseDto); // 응답 본문에 LikeDeleteResponseDto 포함
    }

}
