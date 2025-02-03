package org.example.carebridge.domain.payment.dto.refund;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPayRefundResponseDto {

    private String aid; // 요청 고유 번호
    private String tid; // 결제 고유 번호
    private String cid; // 가맹점 코드
    private String status; // 결제 상태
    private Amount amount; // 환불 금액
}
