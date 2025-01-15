package org.example.carebridge.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.entity.BaseEntity;

/**
 * 댓글 엔티티. BaseEntity를 상속하여 생성 및 수정 시간 추적.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 ID

    @Column(nullable = false)
    private String comment;  // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;  // 해당 댓글이 속한 게시판

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 댓글 작성자
}
