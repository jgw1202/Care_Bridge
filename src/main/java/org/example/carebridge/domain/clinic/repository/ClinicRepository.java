package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
