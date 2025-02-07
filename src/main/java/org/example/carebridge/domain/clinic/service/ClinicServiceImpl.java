package org.example.carebridge.domain.clinic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateRequestDto;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateResponseDto;
import org.example.carebridge.domain.clinic.dto.deletemessage.ClinicDeleteResponseDto;
import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.domain.clinic.entity.Participation;
import org.example.carebridge.domain.clinic.enumClass.ClinicStatus;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.clinic.repository.ParticipationRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.example.carebridge.global.exception.BadValueException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicServiceImpl implements ClinicService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final ParticipationRepository participationRepository;

    // 채팅방 생성
    @Override
    public ClinicCreateResponseDto createClinic(ClinicCreateRequestDto dto, UserDetailsImpl userDetails) {
        User doctor = userRepository.findByIdOrElseThrow(dto.getId());

        // TODO : Repository로 로직 옮기기, 에러코드 확인하기
        if (!doctor.getUserRole().equals(UserRole.DOCTOR)) {
            throw new BadValueException(ExceptionType.USER_NOT_FOUND);
        }

        Optional<Participation> existingParticipation = participationRepository.findByUserAndClinic(doctor, userDetails.getUser(), ClinicStatus.NORMAL);

        if (existingParticipation.isPresent()) {
            // 기존 Clinic이 있을 경우 해당 Clinic 반환
            Clinic existingClinic = existingParticipation.get().getClinic();
            if (existingClinic.getClinicStatus().equals(ClinicStatus.NORMAL)) {
                // 해당 클리닉이
                log.info("유저 정보 : {}", userDetails.getUser());
                return ClinicCreateResponseDto.builder()
                        .clinicId(existingClinic.getId())
                        .patientName(userDetails.getUser().getUserName())
                        .doctorName(doctor.getUserName())
                        .clinicName(existingClinic.getName())
                        .build();
            }
        }

        Clinic clinic = Clinic.builder()
                .name(doctor.getUserName() + " 의사와의 상담 : 환자 " + userDetails.getUser().getUserName())
                .clinicStatus(ClinicStatus.NORMAL)
                .build();

        clinicRepository.save(clinic);

        Participation doctorParticipation = new Participation(doctor, clinic);
        Participation patientParticipation = new Participation(userDetails.getUser(), clinic);

        participationRepository.save(doctorParticipation);
        participationRepository.save(patientParticipation);

        return ClinicCreateResponseDto.builder()
                .clinicId(clinic.getId())
                .patientName(userDetails.getUsername())
                .doctorName(doctor.getUserName())
                .clinicName(clinic.getName())
                .build();
    }

    @Override
    public ClinicCreateResponseDto participateClinic(ClinicCreateRequestDto dto, UserDetailsImpl userDetails) {
        User patient = userRepository.findByIdOrElseThrow(dto.getId());

        Optional<Participation> existingParticipation = participationRepository.findByUserAndClinic(userDetails.getUser(), patient, ClinicStatus.NORMAL);

        if (existingParticipation.isPresent()) {
            // 기존 Clinic이 있을 경우 해당 Clinic 반환
            Clinic existingClinic = existingParticipation.get().getClinic();
            log.info("유저 정보 : {}", userDetails.getUser());
            return ClinicCreateResponseDto.builder()
                    .clinicId(existingClinic.getId())
                    .patientName(patient.getUserName())
                    .doctorName(userDetails.getUser().getUserName())
                    .clinicName(existingClinic.getName())
                    .build();
        } else {
            throw new BadValueException(ExceptionType.USER_NOT_FOUND);
        }
    }

    // 채팅방 삭제(기록은 남아있음)
    @Override
    public ClinicDeleteResponseDto deleteClinic(Long clinicId, UserDetailsImpl userDetails) {
        Clinic clinic = clinicRepository.findByIdOrElseThrow(clinicId);
        log.info("삭제 시작 : {}", clinic.getClinicStatus());
        clinic.deleteClinic();
        log.info("삭제 완료 : {}", clinic.getClinicStatus());

        log.info("삭제 확인 : {}", clinicRepository.save(clinic).getClinicStatus());
        return null;
    }
}
