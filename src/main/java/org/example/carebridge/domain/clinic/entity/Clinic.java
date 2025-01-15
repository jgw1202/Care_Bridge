package org.example.carebridge.domain.clinic.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.clinic.enumClass.ClinicStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "clinic")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private ClinicStatus clinicStatus;

    @OneToMany(mappedBy = "clinic")
    private final List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "clinic")
    private final List<Participation> participations = new ArrayList<>();

    public Clinic() {}

    @Builder
    public Clinic(Long id, String name, ClinicStatus clinicStatus) {
        this.id = id;
        this.name = name;
        this.clinicStatus = clinicStatus;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addParticipation(Participation participation) {
        participations.add(participation);
    }

    public void deleteClinic() {
        this.clinicStatus = ClinicStatus.DELETED;
    }
}
