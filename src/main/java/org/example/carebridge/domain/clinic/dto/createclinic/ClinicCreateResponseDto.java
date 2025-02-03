package org.example.carebridge.domain.clinic.dto.createclinic;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClinicCreateResponseDto {
    private Long clinicId;
    private String patientName;
    private String doctorName;
    private String clinicName;

    @Builder
    public ClinicCreateResponseDto(Long clinicId, String patientName, String doctorName, String clinicName) {
        this.clinicId = clinicId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.clinicName = clinicName;
    }
}
