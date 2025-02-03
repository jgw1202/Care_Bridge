package org.example.carebridge.global.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PaymentViewController {

    @GetMapping("/payment/popup")
    public String paymentPopup() {
        return "paymentpopup";
    }
}
