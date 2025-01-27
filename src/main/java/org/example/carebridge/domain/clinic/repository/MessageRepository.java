package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.clinic.id = :clinicId")
    List<Message> findAllByClinicId(Long clinicId);
}
