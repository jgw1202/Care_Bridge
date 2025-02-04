package org.example.carebridge.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.clinichistory.enums.PaymentStatus;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateRequestDto;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateResponseDto;
import org.example.carebridge.domain.payment.dto.get.PaymentGetResponseDto;
import org.example.carebridge.domain.payment.entity.Payment;
import org.example.carebridge.domain.payment.entity.PaymentMethod;
import org.example.carebridge.domain.payment.repository.PaymentRepository;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.PaymentException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClinicRepository clinicRepository;

    public PaymentCreateResponseDto createPayment(PaymentCreateRequestDto dto, Long clinicId) {

        Optional<Payment> checkedPayment = Optional.ofNullable(paymentRepository.findByClinicId(clinicId, PaymentStatus.CANCEL));

        if (checkedPayment.isPresent()) {
            throw new PaymentException(ExceptionType.PAYMENT_EXISTED); // 임시 예외처리
        }

        Payment payment = Payment.builder()
                .price(dto.getPrice())
                .paymentMethod(PaymentMethod.NOTPAY)
                .paymentInfo(dto.getPaymentInfo())
                .paymentStatus(PaymentStatus.PENDING)
                .clinic(clinicRepository.findByIdOrElseThrow(clinicId))
                .build();

        return PaymentCreateResponseDto.builder()
                .payment(paymentRepository.save(payment))
                .build();
    }

    public PaymentGetResponseDto findPaymentStatus(Long clinicId) {

        log.info("서비스 작동 시작");
        Optional<Payment> payment = Optional.ofNullable(paymentRepository.findByClinicId(clinicId, PaymentStatus.CANCEL));
        log.info("결제 정보 확인");

        if (payment.isPresent()) {
            log.info("상태 전송");
            return new PaymentGetResponseDto(payment.get().getPaymentStatus());
        } else {
            log.info("상태 전송 : 없음");
            return new PaymentGetResponseDto(PaymentStatus.NOT_EXIST);
        }
    }

    public void deletePayment(Long clinicId) {
        Payment payment = paymentRepository.findByClinicId(clinicId, PaymentStatus.CANCEL);
        if (payment.getPaymentStatus().equals(PaymentStatus.PENDING)) {
            paymentRepository.delete(payment);
        } else {
            throw new PaymentException(ExceptionType.PAYMENT_NOT_EXISTED);
        }
    }
}
