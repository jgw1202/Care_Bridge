package org.example.carebridge.domain.aichat.dto;

import lombok.Data;

public class AIChatResponseDto {
    @Data
    public static class ChatMessageDTO {
        private String content;
        private String sender;

        public ChatMessageDTO(String aiResponse) {
            this.content = aiResponse;  // 응답받은 내용
            this.sender = "AI";  // 발신자를 AI 로 지정
        }
    }
}
