package org.example.carebridge.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDeleteResponseDto {
    private String message;

    public BoardDeleteResponseDto(String message) {
        this.message = message;
    }
}
