package org.example.carebridge.global.repository;

import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.entity.RefreshToken;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByUser(User user);

    default RefreshToken deleteRefreshTokenById(Long id) {
        return findById(id).orElseThrow(()-> new NotFoundException(ExceptionType.USER_NOT_FOUND));
    }


    default RefreshToken findByUserOrElseThrow(User user) {
        return findByUser(user).orElseThrow(()-> new NotFoundException(ExceptionType.USER_NOT_FOUND));
    }

}
