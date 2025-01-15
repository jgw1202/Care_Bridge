package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
