package org.example.carebridge.domain.payment.dto.create;

import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.payment.entity.Payment;

@Getter
public class PaymentCreateResponseDto {

    private Integer price;
    private String paymentInfo;

    @Builder
    public PaymentCreateResponseDto(Payment payment) {
        this.price = payment.getPrice();
        this.paymentInfo = payment.getPaymentInfo();
    }
}
