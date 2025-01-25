package org.example.carebridge.domain.aichat.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.aichat.dto.AIChatRequestDto;
import org.example.carebridge.domain.aichat.dto.AIChatResponseDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AIChatService {

    // STOMP 메시지를 클라이언트로 보내기 위한 템플릿 객체
    private final SimpMessagingTemplate messagingTemplate;
    private final OpenAIService openAIService;

    // 클라이언트로부터 받은 메시지를 처리
    public void processMessage(AIChatRequestDto.ChatMessageDTO requestDTO) {
        // 클라이언트로부터 입력받은 메세지 저장
        String userMessage = requestDTO.getContent();

        // 건강 관리와 관련된 질문만 처리
        String prompt = generateHealthPrompt(userMessage);

        // 프롬프트에 맞춰 OpenAI에 질문 후 응답 받기
        String aiResponse = openAIService.askOpenAI(prompt);

        // 응답받은 메세지를 DTO에 담기
        AIChatResponseDto.ChatMessageDTO aiMessage = new AIChatResponseDto.ChatMessageDTO(aiResponse);

        // /sub/messages 경로로 전달해 클라이언트가 메세지를 전달받음
        messagingTemplate.convertAndSend("/sub/messages", aiMessage);
    }

    private String generateHealthPrompt(String userMessage) {
        String prompt = """
        ## 이름
        - CB봇
        
        ## 인삿말
        - 안녕하세요! 당신의 건강관리를 책임지는 AI CB 봇입니다! 건강 관리 관련 질문을 해주시면 답변해드립니다!

        ## 기능
        1. **식이요법** 질문에 대한 답변을 구체적으로 해줍니다!
        2. **가벼운 부상**에 대한 대처법을 구체적으로 알려드립니다!
        3. 건강 관리 이외의 질문은 **정중히 사양하고**, 건강 관리쪽 질문을 할 수 있도록 유도합니다!

        ## 규칙
        - 사용자가 건강과 관련된 질문을 하면, 해당 주제에 맞는 답변을 제공하세요.
        - 사용자가 건강과 관련 없는 질문을 하면, 다음과 같은 응답을 하세요: "저는 건강 관련 질문에만 답변할 수 있습니다. 건강에 관련된 질문을 해주세요."

        ## 사용자 질문
        - 사용자 질문: "%s"
        
        ## AI 응답:
        - 
    """.formatted(userMessage);

        // 여기서 OpenAI API로 요청을 보내고, 받은 응답을 반환
        return prompt;
    }




}
