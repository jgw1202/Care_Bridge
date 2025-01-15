package org.example.carebridge.domain.clinic.dto.createclinic;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClinicCreateResponseDto {
    private String patientName;
    private String doctorName;
    private String clinicName;

    @Builder
    public ClinicCreateResponseDto(String patientName, String doctorName, String clinicName) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.clinicName = clinicName;
    }
}
