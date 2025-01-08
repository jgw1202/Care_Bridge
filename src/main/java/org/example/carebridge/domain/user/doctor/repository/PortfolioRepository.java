package org.example.carebridge.domain.user.doctor.repository;

import org.example.carebridge.domain.user.doctor.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
