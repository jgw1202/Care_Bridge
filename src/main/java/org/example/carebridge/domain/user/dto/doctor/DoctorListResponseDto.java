package org.example.carebridge.domain.user.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class DoctorListResponseDto {

    private Long id;

    private String doctorName;

    private String category;

    private String hospitalName;

    private String phoneNumber;

}
