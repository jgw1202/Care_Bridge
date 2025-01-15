package org.example.carebridge.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String tag;
}
