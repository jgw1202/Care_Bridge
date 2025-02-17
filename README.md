# Care-Bridge

---

## 🚀 프로젝트 소개 : 환자와 의사를 이어주는 Care Bridge

현대 사람들은 병원 방문에 꽤 잦은 편이다. 가벼운 증상이라 하더라도 병원에 방문하기 때문에, 병원에서 대기하는 시간이 꽤나 길다.

환자가 한 병원에 몰리게 되면, 오전에 접수를 마감하는 경우도 빈번하고, 도착하더라도 대기시간이 길어 병원 방문에 피로함을 느끼는 사람들도 발생한다.

이러한 문제들을 해결하기 위해, 병원에 방문하지 않고도 진료를 받을 수 있는 서비스를 제공하는 것이 이번 프로젝트의 주 목적이다.

- 문제 파악
    - 가벼운 증상으로 인한 잠깐의 진료에 비해 긴 대기시간으로 병원을 방문하는 사람들이 시간적인 피로감을 느낌.
    - 노약자 및 임산부, 거동이 불편하거나 전염성이 강한 질병을 앓는 사람들은 병원에 방문하는 것 조차 힘듦.
    - 평범한 사람들이라 하더라도, 개인 시간이 적어 상황에 따라 병원에 방문하기 힘들 수 있음.

- 해결 방안
    - 의사와의 비대면 상담으로, 병원 방문 시간을 아낄 수 있음.
    - 커뮤니티를 통해, 현재 병원 상황 및 기타 정보들을 확인할 수 있음.
    - 간단한 질문들은 AI 챗봇을 통해 해답을 구할 수 있음.

---

## 👨‍💻 Period : 2025/1/2 ~ 2025/2/7

---

![image](https://github.com/user-attachments/assets/aaf6b96f-74db-45a3-91aa-67a1cade73ec)


---

## 👨‍💻 팀 구성

- 김영웅(팀장)
    - 프로젝트 일정 및 전체 관리
    - 회원가입/로그인, 구글 소셜 로그인, CI/CD 구현
    - Batch 기능 구현, 인증/인가 구현
- 정건우(부팀장)
    - Redis 동시성 제어
    - OpenAI API 를 활용한 AI 채팅 기능 구현
    - Prometheus / Grafana 를 활용한 모니터링 기능 구현
    - AWS S3 활용한 첨부파일 기능 구현
- 조성민(팀원)
    - 1:1 채팅 기능 구현
    - 단건 결제 및 결제 환불 기능 구현

---

## 👨‍💻 About Project

- 회원가입/로그인
    - 아이디는 이메일 형식
    - Google Social Login 가능
    - 로그인 시, AccessToken, RefreshToken 발급
    - 탈퇴한 이메일 재사용 불가능
    - 비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함
    - 권한 부여 : 환자/의사
- 사용자 차단/신고
    - 사용자 차단하기
    - 사용자 차단해제 하기
    - 차단 사용자 조회하기
    - 사용자 신고하기
- CI/CD
    - Github Action + EC2 + Docker를 활용한 CI/CD 구현
    - deploy.yml, Docker-compose.yml, .env, prometheus.yml 설정으로 간편한 환경변수 및 설정 관리
- Spring Batch
    - Spring Batch를 통한 반복 업무 자동화
    - 신고 누적 사용자 처리 작업 자동화(매일 00:00)
- 게시판
    - 인증된 유저는 누구나 게시판을 이용할 수 있음.
    - 게시판 작성, 수정, 삭제가 가능
- 1:1 상담
    - 환자가 의사를 선택하여 상담을 신청할 수 있음.
    - 의사는 상담 신청이 들어온 환자 중 한명을 선택하여 상담을 진행함.
    - 상담이 완료된 후, 상담료 및 처방전 가격 등을 환자에게 청구할 수 있음.
- AI 챗봇
    - AI 챗봇을 통해 의료와 관련된 간단한 질의를 진행할 수 있음.
    - 사장님은 주문 수락, 주문 상태 변경
    - 사장님은 주문 거절 가능 / 주문 거절 시 필수적으로 사유를 입력해야함 / 주문 거절시 다른 상태로 변경 불가능
    - 사장님은 주문을 받을 때 주문 한 사람이 가게에 주문한 횟수 조회 가능

---

## 🥵 Trouble Shooting & 🚀 Refactoring

- 카카오페이
    - 결제 준비 후, Response 값 전달 과정에서, TID 전달이 제대로 이루어 지지 않는 오류가 발생하였다.
    - 세션을 이용하여 전달을 시도했으나 결제 진행 시, 도메인이 변경되어 세션이 초기화 되었다.
    - Approve URL 내 orderId를 파라미터로 전달하고, 이를 기반으로 TID 추출하여 오류를 수정하였다.
- 1:1 채팅
    - 웹소켓 연결 후 메세지 전송 시, 로그인 정보를 불러오는데 실패하였다.
    - 웹소켓 연결 시, Security에 의해 인증을 시도하나, 전송할 때는 인증을 시도하지 않았다.
    - Config를 따로 구현하여, 해당 Config에서 메세지의 인증 여부를 설정하였다.
- Menu
    - Response에 성공 메시지를 반환하기 위해 ResponseBody를 커스텀하고 @ResponseStatus 어노테이션으로 상태코드를 설정하였다.
- AccessToken 발행 시, 쿠키에 저장되지 않는 오류
    
    현대 기본적인 웹사이트 연결은 https 이다.
    그러나 현 프로젝트에서는 인증서 발급을 구현하지 못하여, http 연결만 제공한다.
    JWT를 통해 발급한 AccessToken을 쿠키에 저장하기 위해선, 설정을 바꿔줘야 한다.
    
    아래의 설정 중, secure(true), sameSite(None) 설정은 Https 연결 시, 사용된다.
    2개의 값을 변경 해주면 해결되는 이슈였다.
    
    ![image](https://github.com/user-attachments/assets/e3818b3b-16a6-4ba2-8154-37bdaa1ff3c9)

    ![image](https://github.com/user-attachments/assets/d5920b7f-ea39-467d-83d3-944d8f2b99d6)
    
    
    AccessToken은 모든 서비스의 기본이기 때문에, 연결 프로토콜을 잘 확인해야 한다.
    
- docker-compose 사용하기
    
    ![image](https://github.com/user-attachments/assets/d41746d4-0860-4fca-bdad-3e1c0a846313)

    
    deploy.yml 중 일부
    
    기존에는 deploy.yml 파일에서 필요한 모든 환경변수 및 설정들을 진행 해 주었다.
    이러한 방법의 단점은, 휴먼에러와 코드의 가독성이 저하된다.
    해결을 위하여 docker-compose를 사용하였다.
    
    docker-compose의 기능은 다음과 같다
    1. 다중 컨테이너 관리
    2. 환경 구성 자동화
    3. 의존성 컨테이너 자동 실행
    4. 단일 명령 실행
    
    위와 같은 특징으로 인해 아래와 같은 코드로 변하게 되었다.
    
    ![image](https://github.com/user-attachments/assets/47305827-2769-423b-8773-6ac0a82f1586)

    
    docker-compose 를 사용하기 위해선, docker-compose.yml 파일과
    환경 변수를 설정해 줄, .env 파일이 필요하다.
    
    이러한 설정을 위해서 ec2에 수동으로 .env 파일을 생성 해 주고, github-action이 완료되길 기다리면 된다.
    
- 잘못된 질문에 대한 답변을 하는 AI ??
    
    API 를 성공적으로 연동하여 AI에게 질문을 하면 답변을 합니다.
    
    하지만,  AI 챗봇 시스템을 구축한 개발자는 사용자가 건강 관리와 관련된 질문만 하도록 제한하는 기능이 필요 했습니다. 이를 위해 프롬프트를 설정하여 AI가 주어진 규칙에 맞춰 응답하도록 하였습니다. 사용자가 건강과 관련 없는 질문을 했을 때, "저는 건강 관련 질문에만 답변할 수 있습니다."라는 답변을 받도록 설정했습니다.
    
    ```jsx
    // 사용자 질문이 건강 관련인지 확인하고, 아닌 경우 사양하는 응답을 반환
    public String filterHealthQuestions(String userMessage) {
        if (!isHealthRelated(userMessage)) {
            return "저는 건강 관련 질문에만 답변할 수 있습니다. 건강에 관련된 질문을 해주세요.";
        }
        return askOpenAI(userMessage);  // 건강 관련 질문에 대해서만 OpenAI 호출
    }
    
    ```
    
    하지만, 시스템이 예상대로 동작하지 않았습니다. 사용자가 건강 관련 질문이 아닌 내용을 물어보았을 때, AI가 여전히 관련 없는 답변을 하는 상황이 발생했습니다. 예를 들어, "오늘 날씨 어때요?"와 같은 질문을 했을 때, AI가 날씨에 대한 답변을 제공하거나, "저는 건강 관련 질문에만 답변할 수 있습니다."라는 응답을 하지 않았습니다.
    
    이를 해결하기 위해, 개발자는 프롬프트 내에 사용자 질문을 올바르게 처리하고, 사용자가 건강 관련이 아닌 질문을 했을 때 시스템이 "정중히 사양"하는 응답을 보내도록 개선하려 했습니다. 프롬프트 내에서 사용자가 질문한 내용이 건강과 관련 없는지 검증하고, 이를 필터링하는 로직을 추가하기로 결정했습니다.
    
    ```jsx
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
    ```
    
    MarkDown 을 활용하여 AI가 보다 명확하게 자신의 역할을 이해할 수 있도록 했습니다.
    
    `enerateHealthPrompt` 메서드를 수정하여 사용자 질문을 필터링하고, 건강 관련이 아닌 질문에 대해서는 AI가 미리 설정된 응답을 보내도록 수정하였습니다. 추가로, AI 응답이 사용자의 질문에 맞는지 검증하고, 프롬프트의 길이를 적절히 관리하여 OpenAI API에서 문제없이 동작하도록 하였습니다.
    
    그 결과, 시스템은 사용자가 건강과 관련 없는 질문을 할 때 "저는 건강 관련 질문에만 답변할 수 있습니다."라는 응답을 정확히 보내게 되었고, AI는 오직 건강 관련 질문에만 답변을 제공하여 의도된 동작을 완전히 구현했습니다.
    

---

## 🛠️ 개발 환경 및 활용 기술 :

- MySql 8.0
- Java 17
- SpringBoot 3.3.5
- Spring Security + JWT
- OAuth 2.0
- Spring Batch
- Redis 3.18
- S3 2.2.6
- lombok
- WebSocket
