package org.example.carebridge.domain.payment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.payment.dto.approve.KakaoPayApproveResponseDto;
import org.example.carebridge.domain.payment.dto.ready.KakaoPayReadyRequestDto;
import org.example.carebridge.domain.payment.dto.ready.KakaoPayReadyResponseDto;
import org.example.carebridge.domain.payment.entity.Payment;
import org.example.carebridge.domain.payment.entity.PaymentMethod;
import org.example.carebridge.domain.payment.repository.PaymentRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    @Value("${kakao.secret}")
    private String secretKey;

    private KakaoPayReadyResponseDto kakaoReadyDto;
    private final PaymentRepository paymentRepository;

    public KakaoPayReadyResponseDto payReady(KakaoPayReadyRequestDto dto) {

        String orderId = UUID.randomUUID().toString();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");                                                            // 가맹점 코드(테스트용)
        parameters.put("partner_order_id", orderId);                                                    // 주문번호
        parameters.put("partner_user_id", "userDetails.getUsername()");                                 // 회원 아이디 -> UserDetails에서 가져오기
        parameters.put("item_name", dto.getName());                                                     // 상품명
        parameters.put("quantity", "1");                                                                // 상품 수량
        parameters.put("total_amount", String.valueOf(dto.getTotalAmount()));                           // 상품 총액
        parameters.put("tax_free_amount", "0");                                                         // 상품 비과세 금액
        parameters.put("approval_url", "http://localhost:8080/api/payments/kakaopay/success?order_id="+orderId);        // 결제 성공 시 URL
        parameters.put("cancel_url", "http://localhost:8080/api/chatrooms/payments/kakaopay/cancel");                   // 결제 취소 시 URL
        parameters.put("fail_url", "http://localhost:8080/api/chatrooms/payments/kakaopay/failed");                     // 결제 실패 시 URL

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        kakaoReadyDto = template.postForObject(url, requestEntity, KakaoPayReadyResponseDto.class);

        // 테스트 코드
        Payment payment = Payment.builder()
                .price(1000)
                .orderId(orderId)
                .paymentInfo("테스트 상품")
                .paymentMethod(PaymentMethod.NOTPAY)
                .tid(kakaoReadyDto.getTid())
                .build();
        paymentRepository.save(payment);

        kakaoReadyDto.setOrderId(orderId);

        return kakaoReadyDto;
    }

    public KakaoPayApproveResponseDto payApprove(String pgToken, String orderId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");                                                                // 가맹점 코드(테스트용)
        parameters.put("tid", paymentRepository.findByOrderId(orderId).getTid());                           // 결제 고유번호
        parameters.put("partner_order_id", orderId);                                                        // 주문번호
        parameters.put("partner_user_id", "userDetails.getUsername()");                                     // 회원 아이디
        parameters.put("pg_token", pgToken);                                                                // 결제승인 요청을 인증하는 토큰

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        KakaoPayApproveResponseDto approveResponseDto = template.postForObject(url, requestEntity, KakaoPayApproveResponseDto.class);

        return approveResponseDto;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + secretKey);  //카카오페이에서 발급받은 secret key 입력
        headers.set("Content-Type", "application/json");

        return headers;
    }
}
