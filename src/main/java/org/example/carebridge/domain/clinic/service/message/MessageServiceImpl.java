package org.example.carebridge.domain.clinic.service.message;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendResponseDto;
import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.domain.clinic.entity.Message;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.clinic.repository.MessageRepository;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public MessageSendResponseDto saveMessage(Long chatroomId, MessageSendRequestDto dto, UserDetailsImpl userDetails) {
        Clinic clinic = clinicRepository.findById(chatroomId).orElseThrow(() -> new IllegalArgumentException("임시 예외처리"));

        Message message = Message.builder()
                .messageContent(dto.getMessage())
                .senderId(userDetails.getUser().getId())
                .clinic(clinic)
                .build();

        messageRepository.save(message);

        return MessageSendResponseDto.builder()
                .clinicName(clinic.getName())
                .userName(userDetails.getUsername())
                .message(dto.getMessage())
                .build();
    }
}
