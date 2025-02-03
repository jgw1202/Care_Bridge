package org.example.carebridge.domain.user.repository;

import org.example.carebridge.domain.user.dto.doctor.DoctorListResponseDto;
import org.example.carebridge.domain.user.dto.doctor.DoctorResponseDto;
import org.example.carebridge.domain.user.dto.patient.PatientResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


    @Query("select new org.example.carebridge.domain.user.dto.doctor.DoctorListResponseDto(u.id, u.userName,dl.category, dl.hospitalName, u.phoneNum)" +
            "from DoctorLicense dl " +
            "join dl.user u " +
            "where dl.user.id = u.id and u.userRole = :userRole")
    List<DoctorListResponseDto> findUserListByUserRole(UserRole userRole);


    @Query("select new org.example.carebridge.domain.user.dto.doctor.DoctorResponseDto(u.userName, u.profileImageUrl,dl.category, p.portfolio, dl.hospitalName) " +
            "from User u " +
            "join fetch DoctorLicense dl on u.id = dl.user.id " +
            "join fetch Portfolio p on dl.id = p.license.id " +
            "where u.id = :id and dl.user.id = u.id and p.license.id = dl.id")
    DoctorResponseDto findUserByUserRole(Long id, UserRole userRole);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Invalid email: " + email));
    }

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new NotFoundException(ExceptionType.USER_NOT_FOUND));
    }

    @Query("select new org.example.carebridge.domain.user.dto.patient.PatientResponseDto(u.id, u.userName, u.address, u.phoneNum) " +
            "from User u " +
            "join Participation p on u = p.user " +
            "where p.clinic = (select p2.clinic from Participation p2 where p2.user = :user) and u.userRole = :userRole")
    List<PatientResponseDto> findAllByParticipation(User user, UserRole userRole);
}
