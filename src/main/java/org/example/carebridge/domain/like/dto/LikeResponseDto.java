package org.example.carebridge.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
    private Long likeId;
    private Long boardId;
    private Long userId;
}
