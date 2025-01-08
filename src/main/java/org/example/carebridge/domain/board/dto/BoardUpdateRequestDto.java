package org.example.carebridge.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String tag;
}
