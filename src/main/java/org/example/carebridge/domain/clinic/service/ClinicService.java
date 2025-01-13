package org.example.carebridge.domain.clinic.service;

import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateRequestDto;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateResponseDto;
import org.example.carebridge.domain.clinic.dto.deletemessage.ClinicDeleteResponseDto;
import org.example.carebridge.global.auth.UserDetailsImple;

public interface ClinicService {

    ClinicCreateResponseDto createClinic(ClinicCreateRequestDto dto, UserDetailsImple userDetails);

    ClinicDeleteResponseDto deleteClinic(Long chatroomId, UserDetailsImple userDetails);
}
