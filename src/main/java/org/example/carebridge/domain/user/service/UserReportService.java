package org.example.carebridge.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.dto.report.UserReportRequestDto;
import org.example.carebridge.domain.user.dto.report.UserReportResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.entity.UserReport;
import org.example.carebridge.domain.user.repository.UserReportRepository;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;

    //사용자 신고하기
    @Transactional
    public UserReportResponseDto reportUser(UserReportRequestDto userReportRequestDto, User reporterUser) {

        //신고 당하는 userId
        Long reportId = userReportRequestDto.getReportedUserId();

        //실제 존재하는 사용자인지 검증
        User checkReportUser = userRepository.findByIdOrElseThrow(reportId);

        UserReport userReport = buildReportUser(userReportRequestDto, reporterUser, checkReportUser.getUserName(), userReportRequestDto.getComment());

        userReportRepository.save(userReport);

        UserReportResponseDto userReportResponseDto = new UserReportResponseDto("신고가 완료되었습니다.", userReportRequestDto.getComment(), checkReportUser.getUserName());

        return userReportResponseDto;


    }

    private UserReport buildReportUser(UserReportRequestDto userReportRequestDto, User reporterUser, String reportedUserName, String comment) {
        return UserReport.builder()
                .reporter(reporterUser)
                .reportedUserId(userReportRequestDto.getReportedUserId())
                .reportedUserName(reportedUserName)
                .comment(comment)
                .build();
    }


}
