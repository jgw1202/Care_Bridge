package org.example.carebridge.domain.payment.dto.refund;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Amount {

    private int total;
    private int tax_free;
    private int tax;
    private int point;
    private int discount;
    private int green_deposit;
}
