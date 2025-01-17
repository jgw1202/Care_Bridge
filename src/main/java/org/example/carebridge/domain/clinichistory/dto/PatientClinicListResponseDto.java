package org.example.carebridge.domain.clinichistory.dto;

import lombok.Getter;
import org.example.carebridge.domain.clinichistory.entity.ClinicHistory;
import org.example.carebridge.domain.clinichistory.enums.PaymentStatus;

@Getter
public class PatientClinicListResponseDto extends ClinicListResponseDto {

    private String doctorName;

    public PatientClinicListResponseDto(ClinicHistory clinicHistory) {
        super(
                clinicHistory.getId(),
                clinicHistory.getPaymentStatus(),
                clinicHistory.getPrescription(),
                clinicHistory.getModifiedAt(),
                clinicHistory.getClinic().getId()
        );

        this.doctorName = clinicHistory.getDoctorUser().getUserName();
    }
}
