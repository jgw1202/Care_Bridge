package org.example.carebridge.domain.clinic.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.dto.MessageGetResponseDto;
import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.domain.clinic.entity.ClinicMessage;
import org.example.carebridge.domain.clinic.entity.Testm;
import org.example.carebridge.domain.clinic.repository.ClinicMessageRepository;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.clinic.repository.ParticipationRepository;
import org.example.carebridge.domain.clinic.repository.TestmRepository;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final ClinicRepository clinicRepository;
    private final ParticipationRepository participationRepository;
    private final ClinicMessageRepository clinicMessageRepository;
    private final TestmRepository testmRepository;

    @Transactional
    public String saveMessage(Long clinicId, MessageSendRequestDto dto, UserDetailsImpl userDetails) {
        Clinic clinic = clinicRepository.findByIdOrElseThrow(clinicId);

        if(participationRepository.findByUserIdAndClinicId(userDetails.getUser().getId(), clinicId).isEmpty()) {
            throw new IllegalArgumentException("임시 예외 처리");
        }

        ClinicMessage message = new ClinicMessage(dto.getMessage(), userDetails.getUser().getUserName(), clinic);
        Testm testm = new Testm("테스트");

        log.info("메세지 : {}, {}", message.getId(), message.getClinic().getId());

        ClinicMessage result = clinicMessageRepository.save(message);
        Testm t = testmRepository.save(testm);

        log.info("테스트 : {}, {}", t.getId(), t.getContent());
        log.info("메세지 : {}", result.getId());
        log.info("메세지 저장 완료 : {}", result.getMessageContent());

        return userDetails.getUser().getUserName() + " : " + dto.getMessage();
    }

    public List<MessageGetResponseDto> findMessage(Long clinicId, UserDetailsImpl userDetails) {

        if (participationRepository.findByUserIdAndClinicId(userDetails.getUser().getId(), clinicId).isEmpty()) {
            throw new IllegalArgumentException("임시 예외 처리");
        }

        List<ClinicMessage> messages = clinicMessageRepository.findAllByClinicId(clinicId);
        log.info("메세지 출력 중 : {}", messages.toString());

        return messages.stream().map(MessageGetResponseDto::toDto).collect(Collectors.toList());
    }
}
