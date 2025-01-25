package org.example.carebridge.domain.board.dto.view;

import lombok.Getter;
import org.example.carebridge.domain.board.entity.Board;

import java.time.LocalDateTime;

@Getter
public class BoardListViewResponseDto {

    private Long id;
    private String title;
    private String tag;
    private String userName;
    private LocalDateTime createdAt;
    private Long views;

    // Board 엔티티를 BoardListViewResponseDto로 변환하는 메서드
    public static BoardListViewResponseDto from(Board board) {
        return new BoardListViewResponseDto(
                board.getId(),
                board.getTitle(),
                board.getTag(),
                board.getUser().getUserName(),  // User 엔티티에서 userName 가져오기
                board.getCreatedAt(), // 작성일
                board.getViews()                // 조회수
        );
    }

    // 생성자
    public BoardListViewResponseDto(Long id, String title, String tag, String userName, LocalDateTime createdAt, Long views) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.userName = userName;
        this.createdAt = createdAt;
        this.views = views;
    }
}
