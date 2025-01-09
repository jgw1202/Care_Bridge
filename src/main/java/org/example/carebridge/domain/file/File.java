package org.example.carebridge.domain.file;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.global.entity.BaseEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String exit;

    @Column(nullable = false)
    private String url;

    /*
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    */
}
