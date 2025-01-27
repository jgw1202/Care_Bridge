package org.example.carebridge.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.payment.dto.approve.KakaoPayApproveResponseDto;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateRequestDto;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateResponseDto;
import org.example.carebridge.domain.payment.dto.ready.KakaoPayReadyRequestDto;
import org.example.carebridge.domain.payment.dto.ready.KakaoPayReadyResponseDto;
import org.example.carebridge.domain.payment.service.KakaoPayService;
import org.example.carebridge.domain.payment.service.PaymentService;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final PaymentService paymentService;

    @PostMapping({"/{clinicId}"})
    public ResponseEntity<PaymentCreateResponseDto> createPayment(@RequestBody PaymentCreateRequestDto dto, @PathVariable Long clinicId) {
        return new ResponseEntity<>(paymentService.createPayment(dto, clinicId), HttpStatus.CREATED);
    }

    @PostMapping("/kakaopay")
    public KakaoPayReadyResponseDto payKakaoReady(@RequestBody KakaoPayReadyRequestDto dto) { // UserDetails 추후에 추가, 유저 정보를 UserDetails에서 가져오기

        KakaoPayReadyResponseDto responseDto = kakaoPayService.payReady(dto);
        log.info("주문 상품 이름: {}", dto.getName());
        log.info("주문 금액: {}", dto.getTotalAmount().toString());
        log.info("결제 고유번호: {}", responseDto.getTid());
        log.info("결제 주문번호: {}", responseDto.getOrderId());

        return responseDto;
    }

    @GetMapping("/kakaopay/success")
    public KakaoPayApproveResponseDto afterPayKakaoRequest(@RequestParam("pg_token") String pgToken, @RequestParam("order_id") String orderId) { // UserDetails 추후에 추가

        log.info("결제 승인 토큰 : {}", pgToken);

        return kakaoPayService.payApprove(pgToken, orderId);
    }

    @GetMapping("/kakaopay/cancel")
    public void payKakaoCancel() {
        throw new PaymentException(ExceptionType.PAY_CANCEL);
    }

    @GetMapping("/kakaopay/failed")
    public void payKakaoFailed() {
        throw new PaymentException(ExceptionType.PAY_FAILED);
    }
}