package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("select p from Participation p where p.user.id = :userId and p.clinic.id = :clinicId")
    List<Participation> findByUserIdAndClinicId(Long userId, Long clinicId);
}
