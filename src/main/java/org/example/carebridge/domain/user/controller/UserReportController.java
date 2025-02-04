package org.example.carebridge.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.dto.report.UserReportRequestDto;
import org.example.carebridge.domain.user.dto.report.UserReportResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserReportRepository;
import org.example.carebridge.domain.user.service.UserReportService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-report")
public class UserReportController {

    private final UserReportService userReportService;


    //사용자 신고
    @PostMapping
    public ResponseEntity<UserReportResponseDto> userReport(
            @RequestBody UserReportRequestDto userReportRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User reporterUser = userDetails.getUser();

        UserReportResponseDto userReportResponseDto = userReportService.reportUser(userReportRequestDto, reporterUser);

        return new ResponseEntity<>(userReportResponseDto, HttpStatus.OK);
    }


}
