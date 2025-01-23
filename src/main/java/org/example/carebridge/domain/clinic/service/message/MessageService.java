package org.example.carebridge.domain.clinic.service.message;

import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendResponseDto;
import org.example.carebridge.global.auth.UserDetailsImpl;

public interface MessageService {

    MessageSendResponseDto saveMessage(Long chatroomId, MessageSendRequestDto dto, UserDetailsImpl userDetails);
}
