package org.example.carebridge.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.comment.dto.CommentDeleteResponseDto;
import org.example.carebridge.domain.like.dto.LikeDeleteResponseDto;
import org.example.carebridge.domain.like.dto.LikeResponseDto;
import org.example.carebridge.domain.like.service.LikeService;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto> createLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {
        LikeResponseDto response = likeService.createLike(boardId, userDetailsImple.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<LikeDeleteResponseDto> deleteLike(
            @PathVariable Long likeId,
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {
        LikeDeleteResponseDto responseDto = likeService.deleteLike(likeId, userDetailsImple.getUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto); // 명시적으로 JSON 설정
    }

}
