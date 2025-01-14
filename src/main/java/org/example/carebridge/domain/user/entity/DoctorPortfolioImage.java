package org.example.carebridge.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name = "doctor_portfolio")
public class DoctorPortfolioImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String url;

    private Long size;

    private String extension;

    private String fileName;

    @CreatedDate
    private LocalDateTime createdAt;

    public DoctorPortfolioImage(User user, String url, Long size, String extension, String fileName) {
        this.user = user;
        this.url = url;
        this.size = size;
        this.extension = extension;
        this.fileName = fileName;
    }
}
