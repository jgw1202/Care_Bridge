package org.example.carebridge.domain.clinic.dto.getMessage;

import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.clinic.entity.Message;

@Getter
public class MessageGetResponseDto {
    private String name;
    private String message;

    @Builder
    public MessageGetResponseDto(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public static MessageGetResponseDto toDto(Message message) {
        return MessageGetResponseDto.builder()
                .name(message.getSender())
                .message(message.getMessageContent())
                .build();
    }
}
