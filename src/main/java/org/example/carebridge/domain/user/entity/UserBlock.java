package org.example.carebridge.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name ="user_block")
@EntityListeners(AuditingEntityListener.class)
public class UserBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocker_id", nullable = false)
    private User user;

    private Long blockedUserId;

    private String blockedUserName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime blockTime;

    public UserBlock() {
    }

    @Builder
    public UserBlock(User user, Long blockedUserId,String blockedUserName, LocalDateTime blockTime) {
        this.user = user;
        this.blockedUserId = blockedUserId;
        this.blockedUserName = blockedUserName;
        this.blockTime = blockTime;
    }

}
