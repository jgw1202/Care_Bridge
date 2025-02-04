package org.example.carebridge.domain.board.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.comment.entity.Comment;
import org.example.carebridge.domain.like.entity.Like;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "board")
@DynamicInsert
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String tag;
    @Column(nullable = false, columnDefinition = "BIGINT default 0")
    private Long views;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fileUrl;

    // 댓글 연관관계 추가 (보드 삭제 시, 연관 댓글도 삭제됨)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 좋아요 연관관계 추가 (보드 삭제 시, 연관 좋아요도 삭제됨)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Board() {}

    @Builder
    public Board(String title, String content, String tag, User user) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.user = user;
    }

    public void updateBoard(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public void updateFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
