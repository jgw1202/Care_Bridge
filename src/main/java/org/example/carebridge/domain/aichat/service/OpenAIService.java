package org.example.carebridge.domain.aichat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    // application.properties 에서 api key 주입
    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    // 요청 api 주소
    private final String apiUrl = "https://api.openai.com/v1/chat/completions";
    // RESTful 웹 서비스 사용위해
    private final RestTemplate restTemplate = new RestTemplate();

    // 질문 받고 응답
    public String askOpenAI(String prompt) {

        // 요청할 모델 지정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // 헤더 바디를 포함한 요청 객체 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        // api 요청 후 응답 받음
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

        // 응답된 메세지 반환
        if (response.getBody() != null) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return message != null ? (String) message.get("content") : "No response content";
            }
        }
        return "Error: No response from OpenAI";
    }
}
