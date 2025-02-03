package org.example.carebridge.domain.clinic.service.message;

import org.example.carebridge.domain.clinic.dto.MessageGetResponseDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.global.auth.UserDetailsImpl;

import java.util.List;

public interface MessageService {

    String saveMessage(Long clinicId, MessageSendRequestDto dto, UserDetailsImpl userDetails);

    List<MessageGetResponseDto> findMessage(Long clinicId, UserDetailsImpl userDetails);
}
