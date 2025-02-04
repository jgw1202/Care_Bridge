package org.example.carebridge.domain.user.dto.report;

import lombok.Getter;

@Getter
public class UserReportRequestDto {

    private Long reportedUserId;

    private String comment;

}
