package org.example.carebridge.domain.aichat.dto;

import lombok.Data;

public class AIChatRequestDto {
    @Data
    public static class ChatMessageDTO {
        // 요청 내용
        private String content;
        // 클라이언트 이름
        private String sender;

    }
}
