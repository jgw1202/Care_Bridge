package org.example.carebridge.domain.file.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.carebridge.domain.board.entity.Board;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String url;

    private Long size;

    private String extension;

    private String fileName;

    @CreatedDate
    private LocalDateTime createdAt;

    public File(Board board, String url, Long size, String extension, String fileName) {
        this.board = board;
        this.url = url;
        this.size = size;
        this.extension = extension;
        this.fileName = fileName;
    }
}
