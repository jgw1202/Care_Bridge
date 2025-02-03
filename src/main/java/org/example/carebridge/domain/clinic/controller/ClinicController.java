package org.example.carebridge.domain.clinic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateRequestDto;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateResponseDto;
import org.example.carebridge.domain.clinic.dto.deletemessage.ClinicDeleteResponseDto;
import org.example.carebridge.domain.clinic.dto.MessageGetResponseDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.service.ClinicService;
import org.example.carebridge.domain.clinic.service.message.MessageService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/clinic")
@RequiredArgsConstructor
@Slf4j
public class ClinicController {

    private final ClinicService clinicService;
    private final MessageService messageService;

    // TODO : valid 추가

    // 상담 생성
    @PostMapping
    public ResponseEntity<ClinicCreateResponseDto> createClinic(@RequestBody ClinicCreateRequestDto dto,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(clinicService.createClinic(dto, userDetails), HttpStatus.CREATED);
    }

    @PostMapping("/participation")
    public ResponseEntity<ClinicCreateResponseDto> participateClinic(@RequestBody ClinicCreateRequestDto dto,
                                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(clinicService.participateClinic(dto, userDetails), HttpStatus.OK);
    }

    // 메세지 전송
    @MessageMapping("/chat/{clinicId}")
    @SendTo("/sub/chat/{clinicId}")
    public String sendMessage(@DestinationVariable Long clinicId,
                                              @Payload MessageSendRequestDto dto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            log.error("인증된 사용자 정보가 없습니다! WebSocket 인증이 실패한 것으로 보입니다.");
        }
        log.info("사용자 {}가 메시지를 보냄: {}", userDetails, dto.getMessage());
        log.info("clinicId: {}, message: {}, 유저 : {}", clinicId, dto.getMessage(), Objects.requireNonNull(userDetails).getUser());
        log.info("인증 정보 : {}", SecurityContextHolder.getContext().getAuthentication());
        return messageService.saveMessage(clinicId, dto, userDetails);
    }

    // 메세지 조회
    @GetMapping("/{clinicId}")
    public ResponseEntity<List<MessageGetResponseDto>> getMessage(@PathVariable Long clinicId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("clinicId: {}", clinicId);
        return new ResponseEntity<>(messageService.findMessage(clinicId, userDetails), HttpStatus.OK);
    }

    // 상담 삭제
    @PostMapping("/{clinicId}")
    public ResponseEntity<ClinicDeleteResponseDto> deleteClinic(@PathVariable Long clinicId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(clinicService.deleteClinic(clinicId, userDetails), HttpStatus.OK);
    }
}
