package org.example.carebridge.domain.user.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReportResponseDto {

    private String message;

    private String comment;

    private String reportedUserName;

}
