package org.example.carebridge.domain.payment.dto.ready;

import lombok.Getter;

@Getter
public class KakaoPayReadyRequestDto {

    private String name;
    private Integer totalAmount;
}
