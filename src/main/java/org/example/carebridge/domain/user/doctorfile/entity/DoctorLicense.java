package org.example.carebridge.domain.user.doctorfile.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.carebridge.domain.user.entity.User;

import java.util.List;

@Entity
@Getter
@Table(name = "doctor_license")
public class DoctorLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String hospitalName;

    private String address;

    private String category;

    public DoctorLicense() {
    }

    public DoctorLicense(User user, String hospitalName, String address, String category) {
        this.user = user;
        this.hospitalName = hospitalName;
        this.address = address;
        this.category = category;
    }
}
