package org.example.carebridge.domain.clinichistory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.domain.clinichistory.enums.PaymentStatus;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.entity.BaseEntity;

@Entity
@NoArgsConstructor
@Getter
public class ClinicHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_user_id")
    private User patientUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_user_id")
    private User doctorUser;

    @Column(nullable = false)
    private String clinicComment;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;

    private String prescription;


}
