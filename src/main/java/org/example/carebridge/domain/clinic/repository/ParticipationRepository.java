package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Participation;
import org.example.carebridge.domain.clinic.enumClass.ClinicStatus;
import org.example.carebridge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("select p from Participation p where p.user.id = :userId and p.clinic.id = :clinicId")
    List<Participation> findByUserIdAndClinicId(Long userId, Long clinicId);

    @Query("select p from Participation p " +
            "where p.user = :user and p.clinic in " +
            "(select p2.clinic from Participation p2 where p2.user = :doctor)" +
            "and p.clinic.clinicStatus = :clinicStatus")
    Optional<Participation> findByUserAndClinic(User doctor, User user, ClinicStatus clinicStatus);
}
