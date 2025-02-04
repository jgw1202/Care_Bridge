package org.example.carebridge.domain.clinichistory.controller;


import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.clinichistory.dto.ClinicListResponseDto;
import org.example.carebridge.domain.clinichistory.dto.ClinicResponseDto;
import org.example.carebridge.domain.clinichistory.service.ClinicHistoryService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClinicHistoryController {

    private final ClinicHistoryService clinicHistoriesService;

    //본인 진료 내역 전체 조회
    @GetMapping("/clinic-histories")
    public ResponseEntity<List<ClinicListResponseDto>> getHistoryList(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ClinicListResponseDto> clinicListResponseDtos
                = clinicHistoriesService.getHistoryList(userDetails.getUser());

        return new ResponseEntity<>(clinicListResponseDtos, HttpStatus.OK);
    }


    //본인 진료 내역 단건 조회
    @GetMapping("/clinic-histories/{id}")
    public ResponseEntity<ClinicResponseDto> getHistory(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("id") Long clinicId) {

        return new ResponseEntity<>(clinicHistoriesService.getHistory(user.getUser(), clinicId), HttpStatus.OK);

    }


}
