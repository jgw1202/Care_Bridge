package org.example.carebridge.domain.user.doctorfile.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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
