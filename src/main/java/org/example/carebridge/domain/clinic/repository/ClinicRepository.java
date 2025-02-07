package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    default Clinic findByIdOrElseThrow(Long clinicId) {
        return findById(clinicId).orElseThrow(()-> new NotFoundException(ExceptionType.CLINIC_NOT_FOUND));
    }
}
