package org.example.carebridge.domain.payment.dto.get;

import lombok.Getter;
import org.example.carebridge.domain.clinichistory.enums.PaymentStatus;

@Getter
public class PaymentGetResponseDto {

    private PaymentStatus paymentStatus;

    public PaymentGetResponseDto(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
