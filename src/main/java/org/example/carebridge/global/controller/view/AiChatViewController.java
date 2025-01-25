package org.example.carebridge.global.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AiChatViewController {

    @GetMapping("/ai")
    public String aichat() {
        return "aichat";
    }
}
