package org.example.carebridge.domain.like.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.user.entity.User;

/**
 * Like 엔티티 클래스. 좋아요 정보를 저장하는 데이터베이스 테이블과 매핑.
 */
@Entity
@NoArgsConstructor // 기본 생성자 자동 생성 (JPA 사용을 위해 필수)
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 자동 생성
@Getter // 모든 필드에 대한 Getter 메서드 자동 생성
@Setter
@Table(name = "likes") // 테이블 이름을 "likes"로 지정 (MySQL 예약어와 충돌 방지)
public class Like {

    /**
     * 기본 키 ID. 자동 생성됨 (IDENTITY 전략 사용).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성 전략을 IDENTITY로 설정
    private Long id;

    /**
     * 좋아요를 누른 사용자.
     * User 엔티티와 다대일 관계를 설정.
     */
    @ManyToOne // 다대일 관계 매핑 (여러 Like가 하나의 User와 연결)
    @JoinColumn(name = "user_id") // 외래 키 컬럼 이름을 "user_id"로 지정
    private User user;

    /**
     * 좋아요가 속한 게시판.
     * Board 엔티티와 다대일 관계를 설정.
     */
    @ManyToOne // 다대일 관계 매핑 (여러 Like가 하나의 Board와 연결)
    @JoinColumn(name = "board_id") // 외래 키 컬럼 이름을 "board_id"로 지정
    private Board board;
}
