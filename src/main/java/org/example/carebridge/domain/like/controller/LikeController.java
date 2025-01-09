package org.example.carebridge.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.like.dto.LikeResponseDto;
import org.example.carebridge.domain.like.service.LikeService;
import org.example.carebridge.global.auth.UserDetailsImple;
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
    public ResponseEntity<LikeResponseDto> deleteLike(
            @PathVariable Long likeId,
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {
        LikeResponseDto response = likeService.deleteLike(likeId, userDetailsImple.getUser().getId());
        return ResponseEntity.ok(response);
    }
}
