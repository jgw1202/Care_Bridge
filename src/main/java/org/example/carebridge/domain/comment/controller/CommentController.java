package org.example.carebridge.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.comment.dto.CommentDeleteResponseDto;
import org.example.carebridge.domain.comment.dto.CommentRequestDto;
import org.example.carebridge.domain.comment.dto.CommentResponseDto;
import org.example.carebridge.domain.comment.dto.CommentUpdateRequestDto;
import org.example.carebridge.domain.comment.service.CommentService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST 컨트롤러로, 댓글 관련 요청을 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글을 생성하는 엔드포인트.
     *
     * @param boardId         댓글이 달릴 게시판의 ID
     * @param dto             댓글 생성 요청 데이터를 담고 있는 DTO
     * @param userDetailsImpl 인증된 사용자 정보
     * @return 생성된 댓글의 정보
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return new ResponseEntity<>(
                commentService.createComment(boardId, dto, userDetailsImpl.getUser().getId()),
                HttpStatus.CREATED
        );
    }

    /**
     * 댓글을 수정하는 엔드포인트.
     *
     * @param commentId       수정할 댓글의 ID
     * @param dto             수정할 내용을 담고 있는 DTO
     * @param userDetailsImpl 인증된 사용자 정보
     * @return 수정된 댓글의 정보
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return new ResponseEntity<>(
                commentService.updateCommentById(commentId, dto, userDetailsImpl.getUser().getId()),
                HttpStatus.OK
        );
    }

    /**
     * 댓글을 삭제하는 엔드포인트.
     *
     * @param commentId       삭제할 댓글의 ID
     * @param userDetailsImpl 인증된 사용자 정보
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDeleteResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        CommentDeleteResponseDto responseDto =
                commentService.deleteCommentById(commentId, userDetailsImpl.getUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto); // 명시적으로 JSON 설정
    }
}
