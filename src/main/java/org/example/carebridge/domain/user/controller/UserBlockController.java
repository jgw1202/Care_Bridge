package org.example.carebridge.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.dto.block.UserBlockListResponseDto;
import org.example.carebridge.domain.user.dto.block.UserBlockRequestDto;
import org.example.carebridge.domain.user.dto.block.UserBlockResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.service.UserBlockService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-blocks")
public class UserBlockController {

    private final UserBlockService userBlockService;

    //사용자 차단
    @PostMapping
    public ResponseEntity<UserBlockResponseDto> userBlock(
            @RequestBody UserBlockRequestDto userBlockRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User blockerUser = userDetails.getUser();

        UserBlockResponseDto userBlockResponseDto = userBlockService.blockUser(userBlockRequestDto, blockerUser);

        return new ResponseEntity<>(userBlockResponseDto, HttpStatus.OK);
    }

    //차단 사용자 조회
    @GetMapping
    public ResponseEntity<List<UserBlockListResponseDto>> userBlocks(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();

        List<UserBlockListResponseDto> userBlockResponseDtos = userBlockService.getBlockUsers(userId);

        return new ResponseEntity<>(userBlockResponseDtos, HttpStatus.OK);
    }

//    //사용자 차단 해제
    @DeleteMapping
    public void userBlockDelete(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserBlockRequestDto userBlockRequestDto) {

        User blockerUser = userDetails.getUser();

        userBlockService.unBlockUser(userBlockRequestDto, blockerUser);

    }


}
