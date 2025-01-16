package org.example.carebridge.domain.clinichistory.controller;


import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.clinichistory.dto.ClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.dto.DoctorClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.dto.PatientClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.service.ClinicHistoryService;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.example.carebridge.global.exception.BadValueException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clinic-histories")
public class ClinicHistoryController {

    private final ClinicHistoryService clinicHistoriesService;

    //본인 진료 내역 전체 조회
    @GetMapping
    public ResponseEntity<List<ClinicListResponseDto>> getHistoryList(
            @AuthenticationPrincipal UserDetailsImple userDetails) {

        List<ClinicListResponseDto> clinicListResponseDtos
                = clinicHistoriesService.getHistoryList(userDetails.getUser());

        return new ResponseEntity<>(clinicListResponseDtos, HttpStatus.OK);
    }


    //본인 진료 내역 단건 조회


}
