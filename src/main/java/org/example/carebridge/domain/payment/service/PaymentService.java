package org.example.carebridge.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateRequestDto;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateResponseDto;
import org.example.carebridge.domain.payment.entity.Payment;
import org.example.carebridge.domain.payment.entity.PaymentMethod;
import org.example.carebridge.domain.payment.repository.PaymentRepository;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClinicRepository clinicRepository;

    public PaymentCreateResponseDto createPayment(PaymentCreateRequestDto dto, Long clinicId) {

        Payment payment = Payment.builder()
                .price(dto.getPrice())
                .paymentMethod(PaymentMethod.NOTPAY)
                .paymentInfo(dto.getPaymentInfo())
                .clinic(clinicRepository.findByIdOrElseThrow(clinicId))
                .build();

        return PaymentCreateResponseDto.builder()
                .payment(paymentRepository.save(payment))
                .build();
    }
}
