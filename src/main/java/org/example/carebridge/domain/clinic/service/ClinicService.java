package org.example.carebridge.domain.clinic.service;

import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateRequestDto;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateResponseDto;
import org.example.carebridge.domain.clinic.dto.deletemessage.ClinicDeleteResponseDto;
import org.example.carebridge.global.auth.UserDetailsImpl;

public interface ClinicService {

    ClinicCreateResponseDto createClinic(ClinicCreateRequestDto dto, UserDetailsImpl userDetails);

    ClinicDeleteResponseDto deleteClinic(Long chatroomId, UserDetailsImpl userDetails);
}
