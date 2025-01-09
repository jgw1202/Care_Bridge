package org.example.carebridge.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateRequestDto {
    private Long id;
    private String comment;
}
