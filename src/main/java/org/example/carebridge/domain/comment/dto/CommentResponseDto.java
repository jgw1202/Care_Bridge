package org.example.carebridge.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
