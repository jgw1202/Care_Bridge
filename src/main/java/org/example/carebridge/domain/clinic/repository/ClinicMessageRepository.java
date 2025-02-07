package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.ClinicMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicMessageRepository extends JpaRepository<ClinicMessage, Long> {

    @Query("select m from ClinicMessage m where m.clinic.id = :clinicId")
    List<ClinicMessage> findAllByClinicId(@Param("clinicId") Long clinicId);
}
