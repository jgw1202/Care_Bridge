package org.example.carebridge.domain.clinic.message;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.global.entity.BaseEntity;

@Getter
@Table(name = "message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String messageContent;

    @JoinColumn(nullable = false)
    private Long senderId;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public Message() {}

    public Message(String messageContent, Long senderId, Clinic clinic) {
        this.messageContent = messageContent;
        this.senderId = senderId;
        this.clinic = clinic;
    }
}
