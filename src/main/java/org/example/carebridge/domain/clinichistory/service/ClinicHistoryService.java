package org.example.carebridge.domain.clinichistory.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.clinichistory.dto.ClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.dto.DoctorClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.dto.PatientClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.entity.ClinicHistory;
import org.example.carebridge.domain.clinichistory.repository.ClinicHistoryRepository;
import org.example.carebridge.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicHistoryService {

    private final ClinicHistoryRepository clinicHistoryRepository;

    @Transactional
    public List<ClinicListResponseDto> getHistoryList(User user) {

        List<ClinicListResponseDto> clinicListResponseDtos = new ArrayList<>();
        Long userId = user.getId();


        //권한에 따른 Dto 분리
        if(user.isDoctor()) {
            List<ClinicHistory> clinicHistories = clinicHistoryRepository.findByDoctorUserId(userId);
            for(ClinicHistory clinicHistory : clinicHistories) {
                clinicListResponseDtos.add(new DoctorClinicListResponseDto(clinicHistory));
            }
            return clinicListResponseDtos;
        }

        else {
            List<ClinicHistory> clinicHistories = clinicHistoryRepository.findByPatientUserId(userId);
            for(ClinicHistory clinicHistory : clinicHistories) {
                clinicListResponseDtos.add(new PatientClinicListResponseDto(clinicHistory));
            }
            return clinicListResponseDtos;
        }
    }
}
