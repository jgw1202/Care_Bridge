package org.example.carebridge.domain.user.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.carebridge.domain.user.doctorfile.entity.DoctorLicense;
import org.example.carebridge.domain.user.doctorfile.entity.Portfolio;

@Getter
@AllArgsConstructor
public class DoctorResponseDto {

    private String doctorName;

    private String doctorProfile;

    private String category;

    private String portfolio;

    private String hospitalName;

    public DoctorResponseDto(String doctorName, String doctorProfile, DoctorLicense doctorLicense, Portfolio portfolio) {
        this.doctorName = doctorName;
        this.doctorProfile = doctorProfile;
        this.category = doctorLicense.getCategory();
        this.portfolio = portfolio.getPortfolio();
        this.hospitalName = doctorLicense.getHospitalName();
    }
}
