package org.example.carebridge.domain.clinic.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.dto.MessageGetResponseDto;
import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.domain.clinic.entity.Message;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.clinic.repository.MessageRepository;
import org.example.carebridge.domain.clinic.repository.ParticipationRepository;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ClinicRepository clinicRepository;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    @Override
    public String saveMessage(Long clinicId, MessageSendRequestDto dto, UserDetailsImpl userDetails) {
        Clinic clinic = clinicRepository.findByIdOrElseThrow(clinicId);

        if(participationRepository.findByUserIdAndClinicId(userDetails.getUser().getId(), clinicId).isEmpty()) {
            throw new IllegalArgumentException("임시 예외 처리");
        }

        Message message = Message.builder()
                .messageContent(dto.getMessage())
                .sender(userDetails.getUser().getUserName())
                .clinic(clinic)
                .build();

        log.info("메세지 : {}", message.toString());

        messageRepository.save(message);
        log.info("메세지 저장 완료");

        return userDetails.getUser().getUserName() + " : " + dto.getMessage();
    }

    @Override
    public List<MessageGetResponseDto> findMessage(Long clinicId, UserDetailsImpl userDetails) {

        if (participationRepository.findByUserIdAndClinicId(userDetails.getUser().getId(), clinicId).isEmpty()) {
            throw new IllegalArgumentException("임시 예외 처리");
        }

        List<Message> messages = messageRepository.findAllByClinicId(clinicId);
        log.info("메세지 출력 중");

        return messages.stream().map(MessageGetResponseDto::toDto).collect(Collectors.toList());
    }
}
