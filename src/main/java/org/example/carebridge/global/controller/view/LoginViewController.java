package org.example.carebridge.global.controller.view;

import jakarta.servlet.http.HttpServletResponse;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup-patient")
    public String patientSignup() {
        return "signup-patient";
    }

    @GetMapping("/signup-doctor")
    public String doctorSignup() { return "signup-doctor"; }

    @GetMapping("/signup-social")
    public String signup(@RequestParam String email, @RequestParam String name, Model model) {

        model.addAttribute("email", email);
        model.addAttribute("name", name);

        return "signup-social";
    }

    @GetMapping("/dashboard")
    public String getDashboard(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        // 사용자 이름 가져오기
        String userName = userDetails.getUser().getUserName();
        // 모델에 사용자 이름 추가
        model.addAttribute("userName", userName);

        // 대시보드 HTML 반환
        return "dashboard";
    }

    @GetMapping("/login-social")
    public String afterGoogleLogin(@RequestParam String accessToken, @RequestParam String refreshToken, HttpServletResponse response,
                                   Model model) {

        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken);
        ResponseCookie cookie = ResponseCookie.from("Authorization", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600)
//                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return "login-social";
    }


}
