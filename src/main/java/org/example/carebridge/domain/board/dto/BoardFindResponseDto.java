package org.example.carebridge.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.board.entity.Board;

@Getter
public class BoardFindResponseDto {
    private Long id;
    private String title;
    private String content;
    private String tag;
    private Long view;

    @Builder
    public BoardFindResponseDto(Long id, String title, String content, String tag, Long view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.view = view;
    }

    public static BoardFindResponseDto toDto(Board board) {
        return BoardFindResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .tag(board.getTag())
                .view(board.getViews())
                .build();
    }
}
