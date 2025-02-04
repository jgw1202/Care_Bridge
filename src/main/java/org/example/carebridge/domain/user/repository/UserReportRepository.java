package org.example.carebridge.domain.user.repository;

import org.example.carebridge.domain.user.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

}
