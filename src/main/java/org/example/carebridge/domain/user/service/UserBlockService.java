package org.example.carebridge.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.dto.block.UserBlockListResponseDto;
import org.example.carebridge.domain.user.dto.block.UserBlockRequestDto;
import org.example.carebridge.domain.user.dto.block.UserBlockResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.entity.UserBlock;
import org.example.carebridge.domain.user.repository.UserBlockRepository;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.exception.BadRequestException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserBlockService {

    private final UserBlockRepository userBlockRepository;
    private final UserRepository userRepository;

    // 사용자 차단하기
    @Transactional
    public UserBlockResponseDto blockUser(UserBlockRequestDto userBlockRequestDto, User blockerUser) {

        //차단 할 userId
        Long blockedUserId = userBlockRequestDto.getUserId();

        //차단 할 userId 유무 확인
        User checkBlockedUserId = userRepository.findByIdOrElseThrow(blockedUserId);

        //이미 차단 된 사용자인지 검증
        Optional<UserBlock> existUserBlock = userBlockRepository.findUserBlockById(blockedUserId);

        if(existUserBlock.isPresent()) {
            throw new BadRequestException(ExceptionType.EXIST_USER);
        }

        //UserBlockRepository에 저장할 객체 생성
        UserBlock userBlock = buildBlockUser(userBlockRequestDto, blockerUser, checkBlockedUserId.getUserName());

        userBlockRepository.save(userBlock);

        return new UserBlockResponseDto(checkBlockedUserId.getUserName(), "요청하신 사용자가 차단되었습니다.");

    }

    // 차단 된 사용자 조회
    @Transactional
    public List<UserBlockListResponseDto> getBlockUsers(Long userId) {

        List<UserBlockListResponseDto> blockList = new ArrayList<>();

        List<UserBlockListResponseDto> findBlockList = userBlockRepository.findBlockedUserIdByUserId(userId);

        for(UserBlockListResponseDto block : findBlockList) {
            blockList.add(new UserBlockListResponseDto(
                    block.getId(),
                    block.getUserName(),
                    block.getBlockTime()
            ));
        }

        return findBlockList;

    }

    // 사용자 차단 해제하기

    @Transactional
    public void unBlockUser(UserBlockRequestDto userBlockRequestDto,  User blockerUser) {

        //차단 해제 할 userId
        Long blockedUserId = userBlockRequestDto.getUserId();

        Long blockerUserId = blockerUser.getId();

        //실제 존재하는 유저인지 확인
        User checkBlockedUserId = userRepository.findByIdOrElseThrow(blockedUserId);

        //차단 된 유저인지 확인
        Optional<UserBlock> existUserBlock = userBlockRepository.findExistUserById(blockedUserId, blockerUserId);

        //없다면 차단 되지 않은 유저로 예외처리
        if(!existUserBlock.isPresent()) {
            throw new BadRequestException(ExceptionType.NOT_BLOCK_USER);
        }
        else {
            userBlockRepository.deleteBlockUser(blockedUserId, blockerUserId);
        }

    }

    private UserBlock buildBlockUser(UserBlockRequestDto userBlockRequestDto, User blockerUser, String blockedUserName) {
        return UserBlock.builder()
                .user(blockerUser)
                .blockedUserId(userBlockRequestDto.getUserId())
                .blockedUserName(blockedUserName)
                .build();
    }

}
