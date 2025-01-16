package org.example.carebridge.domain.clinichistory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.carebridge.domain.clinichistory.enums.PaymentStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClinicListResponseDto {
    private Long id;

    private PaymentStatus payMentStatus;

    private String prescription;

    private LocalDateTime modifiedAt;

    private Long clinicId;

    public ClinicListResponseDto(Long id, PaymentStatus payMentStatus, String prescription, LocalDateTime modifiedAt, Long clinicId) {
        this.id = id;
        this.payMentStatus = payMentStatus;
        this.prescription = prescription;
        this.modifiedAt = modifiedAt;
        this.clinicId = clinicId;
    }
}
