package org.example.carebridge.domain.clinichistory.repository;

import org.example.carebridge.domain.clinichistory.entity.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {

    @Query("select c from ClinicHistory c where c.patientUser.id = :patientUserId")
    List<ClinicHistory> findByPatientUserId(Long patientUserId);

    @Query("select c from ClinicHistory c where c.doctorUser.id = :doctorUserId")
    List<ClinicHistory> findByDoctorUserId(Long doctorUserId);

    @Query(" select c from ClinicHistory c where c.doctorUser.id = :doctorUserId and c.clinic.id = :clinicId")
    ClinicHistory findByDoctorUserIdAndClinicId(Long doctorUserId, Long clinicId);

    @Query(" select c from ClinicHistory c where c.patientUser.id = :patientUserId and c.clinic.id = :clinicId")
    ClinicHistory findByPatientUserIdAndClinicId(Long patientUserId, Long clinicId);


}