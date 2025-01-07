package org.example.carebridge.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardCreateRequestDto {
    private String title;
    private String content;
    private String tag;
}
