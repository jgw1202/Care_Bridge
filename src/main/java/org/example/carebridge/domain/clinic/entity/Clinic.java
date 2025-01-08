package org.example.carebridge.domain.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.carebridge.domain.clinic.enumClass.ClinicStatus;
import org.example.carebridge.domain.clinic.message.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "clinic")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String name;

    @JoinColumn(nullable = false)
    private String status;

    @JoinColumn(nullable = false)
    private ClinicStatus clinicStatus;

    @OneToMany(mappedBy = "clinic")
    private final List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "clinic")
    private final List<Participation> participations = new ArrayList<>();

    public Clinic() {}

    public Clinic(Long id, String name, String status, LocalDate date, ClinicStatus clinicStatus) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.clinicStatus = clinicStatus;
    }
}
