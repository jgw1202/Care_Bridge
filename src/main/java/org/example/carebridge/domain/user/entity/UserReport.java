package org.example.carebridge.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user_report")
@EntityListeners(AuditingEntityListener.class)
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    private Long reportedUserId;

    private String reportedUserName;

    private String comment;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime reportTime;

    public UserReport() {
    }

    @Builder
    public UserReport(User reporter, Long reportedUserId, String reportedUserName, String comment, LocalDateTime reportTime) {
        this.reporter = reporter;
        this.reportedUserId = reportedUserId;
        this.reportedUserName = reportedUserName;
        this.comment = comment;
        this.reportTime = reportTime;
    }
}
