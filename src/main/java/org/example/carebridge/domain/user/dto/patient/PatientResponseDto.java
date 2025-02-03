package org.example.carebridge.domain.user.dto.patient;

import lombok.Getter;

@Getter
public class PatientResponseDto {

    private Long id;

    private String patientName;

    private String address;

    private String phoneNumber;

    public PatientResponseDto(Long id, String patientName, String address, String phoneNumber) {
        this.id = id;
        this.patientName = patientName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
