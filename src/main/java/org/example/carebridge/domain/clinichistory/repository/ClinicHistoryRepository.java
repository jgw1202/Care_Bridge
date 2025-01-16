package org.example.carebridge.domain.clinichistory.repository;

import org.example.carebridge.domain.clinichistory.entity.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {

    @Query("select c from ClinicHistory c where c.patientUser.id = :patientUserId")
    List<ClinicHistory> findByPatientUserId(Long patientUserId);

    @Query("select c from ClinicHistory c where c.doctorUser.id = :doctorUserId")
    List<ClinicHistory> findByDoctorUserId(Long doctorUserId);
}