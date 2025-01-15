package org.example.carebridge.domain.clinic.dto.sendmessage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageSendResponseDto {
    private String clinicName;
    private String userName;
    private String message;

    @Builder
    public MessageSendResponseDto(String clinicName, String userName, String message) {
        this.clinicName = clinicName;
        this.userName = userName;
        this.message = message;
    }
}
