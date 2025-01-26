package org.example.carebridge.domain.board.dto.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.carebridge.domain.board.entity.Board;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardViewResponseDto {

    private Long id;          // 게시글 ID
    private String title;     // 게시글 제목
    private String content;   // 게시글 내용
    private String tag;       // 게시글 태그
    private String userName;
    private LocalDateTime createdAt; // 생성 날짜

    // 생성 메서드 - Entity를 DTO로 변환
    public static BoardViewResponseDto fromEntity(Board board) {
        return new BoardViewResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getTag(),
                board.getUser().getUserName(),
                board.getCreatedAt()
        );
    }
}
