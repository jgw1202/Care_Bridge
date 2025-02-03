package org.example.carebridge.domain.user.dto.block;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBlockResponseDto {

    private String message;

    private String userName;

    public UserBlockResponseDto(String message, String userName) {
        this.message = message;
        this.userName = userName;
    }

    public UserBlockResponseDto(String message) {
        this.message = message;
    }


}
