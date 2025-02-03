package org.example.carebridge.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.payment.dto.approve.KakaoPayApproveResponseDto;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateRequestDto;
import org.example.carebridge.domain.payment.dto.create.PaymentCreateResponseDto;
import org.example.carebridge.domain.payment.dto.get.PaymentGetResponseDto;
import org.example.carebridge.domain.payment.dto.ready.KakaoPayReadyRequestDto;
import org.example.carebridge.domain.payment.dto.ready.KakaoPayReadyResponseDto;
import org.example.carebridge.domain.payment.dto.refund.KakaoPayRefundResponseDto;
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

    // 결제 정보 생성
    @PostMapping({"/{clinicId}"})
    public ResponseEntity<PaymentCreateResponseDto> createPayment(@RequestBody PaymentCreateRequestDto dto, @PathVariable Long clinicId) {
        return new ResponseEntity<>(paymentService.createPayment(dto, clinicId), HttpStatus.CREATED);
    }

    // 결제 상태 조회
    @GetMapping("/{clinicId}")
    public ResponseEntity<PaymentGetResponseDto> getPaymentStatus(@PathVariable Long clinicId) {
        return new ResponseEntity<>(paymentService.findPaymentStatus(clinicId), HttpStatus.OK);
    }

    // 결제 정보 삭제
    @DeleteMapping("/{clinicId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long clinicId) {
        paymentService.deletePayment(clinicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/kakaopay")
    public KakaoPayReadyResponseDto KakaoPayReady(@RequestBody KakaoPayReadyRequestDto dto) { // UserDetails 추후에 추가, 유저 정보를 UserDetails에서 가져오기

        KakaoPayReadyResponseDto responseDto = kakaoPayService.payReady(dto);
        log.info("주문 상품 이름: {}", dto.getName());
        log.info("주문 금액: {}", dto.getTotalAmount().toString());
        log.info("결제 고유번호: {}", responseDto.getTid());
        log.info("결제 주문번호: {}", responseDto.getOrderId());

        return responseDto;
    }

    @GetMapping("/kakaopay/success")
    public KakaoPayApproveResponseDto afterKakaoPayRequest(@RequestParam("pg_token") String pgToken, @RequestParam("order_id") String orderId) { // UserDetails 추후에 추가

        log.info("결제 승인 토큰 : {}", pgToken);

        return kakaoPayService.payApprove(pgToken, orderId);
    }

    @PostMapping("/kakaopay/refund/{clinicId}")
    public KakaoPayRefundResponseDto kakaoPayRefund(@PathVariable Long clinicId) {

        return kakaoPayService.payRefund(clinicId);
    }

    @GetMapping("/kakaopay/cancel")
    public void kakaoPayCancel() {
        throw new PaymentException(ExceptionType.PAY_CANCEL);
    }

    @GetMapping("/kakaopay/failed")
    public void kakaoPayFailed() {
        throw new PaymentException(ExceptionType.PAY_FAILED);
    }
}