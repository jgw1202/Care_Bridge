package org.example.carebridge.domain.user.doctorfile.repository;

import org.example.carebridge.domain.user.doctorfile.entity.DoctorLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorLicenseRepository extends JpaRepository<DoctorLicense, Long> {
}
