package org.example.carebridge.domain.like.repository;

import org.example.carebridge.domain.like.entity.Like;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * LikeRepository 인터페이스.
 * JPA를 사용하여 Like 엔티티에 대한 데이터베이스 접근을 제공.
 * 기본적인 CRUD 기능을 JpaRepository로부터 상속받아 사용 가능.
 */
@Repository // Spring에서 이 인터페이스를 Repository로 인식하고 관리하도록 설정
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * ID를 기반으로 Like 엔티티를 조회하거나, 없으면 예외를 던지는 메서드.
     *
     * @param id 조회할 Like의 ID
     * @return 조회된 Like 엔티티
     * @throws NotFoundException 지정한 ID의 Like가 존재하지 않을 때 발생
     */
    default Like findByIdOrElseThrow(Long id) {
        // ID를 사용하여 Like 엔티티를 조회하고, Optional이 비어있으면 예외를 던짐
        return findById(id).orElseThrow(() -> new NotFoundException(ExceptionType.LIKE_NOT_FOUND));
    }
}
