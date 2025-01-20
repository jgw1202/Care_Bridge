package org.example.carebridge.domain.aichat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/ai")
    public String aichat() {
        return "aichat";
    }
}
