package org.example.carebridge.domain.user.repository;

import org.example.carebridge.domain.user.dto.block.UserBlockListResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.entity.UserBlock;
import org.example.carebridge.global.exception.BadRequestException;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

    Optional<UserBlock> findUserBlockById(Long id);

    @Query("select new org.example.carebridge.domain.user.entity.UserBlock(ub.user, ub.blockedUserId,ub.blockedUserName, ub.blockTime)" +
            "from UserBlock ub " +
            "where ub.blockedUserId = :id and ub.user.id = :blockerId")
    Optional<UserBlock> findExistUserById(Long id, Long blockerId);

    @Modifying
    @Query("delete from UserBlock ub " +
            "where ub.blockedUserId = :id and ub.user.id = :blockerId")
    void deleteBlockUser(Long id, Long blockerId);



    @Query("select new org.example.carebridge.domain.user.dto.block.UserBlockListResponseDto(ub.blockedUserId, ub.blockedUserName, ub.blockTime) " +
            "from UserBlock ub " +
            "where ub.user.id = :userId")
    List<UserBlockListResponseDto> findBlockedUserIdByUserId(Long userId);
}
