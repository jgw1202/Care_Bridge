package org.example.carebridge.domain.user.dto.block;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserBlockListResponseDto {

    private Long id;

    private String userName;

    private LocalDateTime blockTime;

}
