package org.example.carebridge.domain.user.repository;

import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {


    @Query("SELECT u FROM User u WHERE u.id IN " +
            "(SELECT ur.reportedUserId FROM UserReport ur GROUP BY ur.reportedUserId HAVING COUNT(ur.reportedUserId) >= 30)")
    List<User> findUsersToReport();

}
