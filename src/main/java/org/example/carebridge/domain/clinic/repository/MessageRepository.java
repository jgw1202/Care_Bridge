package org.example.carebridge.domain.clinic.repository;

import org.example.carebridge.domain.clinic.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
