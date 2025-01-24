package org.example.carebridge.domain.payment.dto.create;

import lombok.Getter;

@Getter
public class PaymentCreateRequestDto {

    private Integer price;
    private String paymentInfo;
}
