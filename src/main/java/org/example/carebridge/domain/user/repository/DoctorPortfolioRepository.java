package org.example.carebridge.domain.user.repository;

import org.example.carebridge.domain.user.entity.DoctorPortfolioImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorPortfolioRepository extends JpaRepository<DoctorPortfolioImage, Long> {
}
