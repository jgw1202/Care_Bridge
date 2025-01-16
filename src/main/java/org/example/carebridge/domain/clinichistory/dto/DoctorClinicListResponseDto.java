package org.example.carebridge.domain.clinichistory.dto;

import lombok.Getter;
import org.example.carebridge.domain.clinichistory.entity.ClinicHistory;

@Getter
public class DoctorClinicListResponseDto extends ClinicListResponseDto{

    private String patientName;

    private String ClinicComment;

    public DoctorClinicListResponseDto(ClinicHistory clinicHistory) {
        super(
                clinicHistory.getId(),
                clinicHistory.getPaymentStatus(),
                clinicHistory.getPrescription(),
                clinicHistory.getModifiedAt(),
                clinicHistory.getClinic().getId()
        );
        this.patientName = clinicHistory.getPatientUser().getUserName();
        this.ClinicComment = clinicHistory.getClinicComment();
    }
}
