package org.example.carebridge.domain.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.carebridge.domain.user.entity.User;

@Getter
@Entity
@Table(name = "participation")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public Participation() {}

    public Participation(User user, Clinic clinic) {
        this.clinic = clinic;
        this.user = user;
    }
}
