package org.example.carebridge.domain.user.doctor.repository;

import org.example.carebridge.domain.user.doctor.entity.DoctorLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorLicenseRepository extends JpaRepository<DoctorLicense, Long> {
}
