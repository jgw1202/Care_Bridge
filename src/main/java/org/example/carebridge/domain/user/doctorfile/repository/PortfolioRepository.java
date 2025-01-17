package org.example.carebridge.domain.user.doctorfile.repository;

import org.example.carebridge.domain.user.doctorfile.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
