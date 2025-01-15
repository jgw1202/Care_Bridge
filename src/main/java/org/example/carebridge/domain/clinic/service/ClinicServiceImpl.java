package org.example.carebridge.domain.clinic.service;

import lombok.RequiredArgsConstructor;
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
import org.example.carebridge.global.auth.UserDetailsImple;
import org.example.carebridge.global.exception.BadValueException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final ParticipationRepository participationRepository;

    // 채팅방 생성
    @Override
    public ClinicCreateResponseDto createClinic(ClinicCreateRequestDto dto, UserDetailsImple userDetails) {
        User user = userRepository.findByIdOrElseThrow(dto.getDoctorId());

        // TODO : Repository로 로직 옮기기, 에러코드 확인하기
        if (!user.getUserRole().equals(UserRole.DOCTOR)) {
            throw new BadValueException(ExceptionType.USER_NOT_FOUND);
        }

        Clinic clinic = Clinic.builder()
                .name(user.getUserName() + " 의사와의 상담 : 환자 " + userDetails.getUser().getUserName())
                .clinicStatus(ClinicStatus.NORMAL)
                .build();

        Participation participation = new Participation(user, clinic);

        clinicRepository.save(clinic);
        participationRepository.save(participation);

        return ClinicCreateResponseDto.builder()
                .patientName(userDetails.getUsername())
                .doctorName(user.getUserName())
                .clinicName(clinic.getName())
                .build();
    }

    // 채팅방 삭제(기록은 남아있음)
    @Override
    public ClinicDeleteResponseDto deleteClinic(Long chatroomId, UserDetailsImple userDetails) {
        Clinic clinic = clinicRepository.findById(chatroomId).orElseThrow(() -> new IllegalArgumentException("임시 예외처리"));
        return null;
    }
}
