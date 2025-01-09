package org.example.carebridge.domain.user.repository;

import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()-> new IllegalArgumentException("Invalid email: " + email));
    }

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new NotFoundException(ExceptionType.USER_NOT_FOUND));
    }

}
