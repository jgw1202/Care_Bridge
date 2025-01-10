package org.example.carebridge.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.comment.dto.CommentDeleteResponseDto;
import org.example.carebridge.domain.comment.dto.CommentRequestDto;
import org.example.carebridge.domain.comment.dto.CommentResponseDto;
import org.example.carebridge.domain.comment.dto.CommentUpdateRequestDto;
import org.example.carebridge.domain.comment.service.CommentService;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto dto,
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {
        return new ResponseEntity<>(commentService.createComment(boardId, dto, userDetailsImple.getUser().getId()), HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto dto,
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {
        return new ResponseEntity<>(commentService.updateCommentById(commentId, dto, userDetailsImple.getUser().getId()), HttpStatus.OK);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDeleteResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {
        CommentDeleteResponseDto responseDto = commentService.deleteCommentById(commentId, userDetailsImple.getUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto); // 명시적으로 JSON 설정
    }



}
