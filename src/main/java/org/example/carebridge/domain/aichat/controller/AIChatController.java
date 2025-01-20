package org.example.carebridge.domain.aichat.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.aichat.dto.AIChatRequestDto;
import org.example.carebridge.domain.aichat.service.AIChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/ai")
public class AIChatController {

    private final AIChatService chatService;

    // 클라이언트가 입력한 메세지 서버로 전달
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload AIChatRequestDto.ChatMessageDTO chatMessage) {
        chatService.processMessage(chatMessage);
    }
    // 클라이언트 정보 저장
    @MessageMapping("/addUsers")
    public void addUser(@Payload AIChatRequestDto.ChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    }
}