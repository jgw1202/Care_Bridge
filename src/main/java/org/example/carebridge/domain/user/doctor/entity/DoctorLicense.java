package org.example.carebridge.domain.user.doctor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.carebridge.domain.user.entity.User;

@Entity
@Getter
@Table(name = "doctor_license")
public class DoctorLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String hospitalName;

    public DoctorLicense() {
    }

    public DoctorLicense(User user,String hospitalName ) {
        this.user = user;
        this.hospitalName = hospitalName;
    }
}
