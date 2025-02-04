package org.example.carebridge.domain.clinichistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.carebridge.domain.clinichistory.enums.PaymentStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ClinicResponseDto {
    private Long id;

    private PaymentStatus payMentStatus;

    private String prescription;

    private LocalDateTime modifiedAt;

    private Long clinicId;

}
