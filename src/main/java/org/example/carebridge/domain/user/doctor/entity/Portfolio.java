package org.example.carebridge.domain.user.doctor.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.example.carebridge.domain.user.dto.signup.UserDoctorSignupRequestDto;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_license_id")
    private DoctorLicense license;

    private String portfolio;

    public Portfolio() {
    }

    public Portfolio(DoctorLicense license, String portfolio) {
        this.license = license;
        this.portfolio = portfolio;
    }
}
