package org.example.carebridge.domain.clinic.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    private Long senderId;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public Message() {}

    @Builder
    public Message(String messageContent, Long senderId, Clinic clinic) {
        this.messageContent = messageContent;
        this.senderId = senderId;
        this.clinic = clinic;
    }
}
