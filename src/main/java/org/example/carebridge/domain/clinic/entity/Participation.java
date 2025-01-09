package org.example.carebridge.domain.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.carebridge.domain.user.entity.User;

@Getter
@Table(name = "participation")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
