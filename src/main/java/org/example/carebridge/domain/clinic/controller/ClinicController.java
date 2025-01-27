package org.example.carebridge.domain.clinic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateRequestDto;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateResponseDto;
import org.example.carebridge.domain.clinic.dto.deletemessage.ClinicDeleteResponseDto;
import org.example.carebridge.domain.clinic.dto.getMessage.MessageGetResponseDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.service.ClinicService;
import org.example.carebridge.domain.clinic.service.message.MessageService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 메세지 전송
    @MessageMapping("/chat/{clinicId}")
    @SendTo("/sub/chat/{clinicId}")
    public String sendMessage(@DestinationVariable Long clinicId,             // MessageSendResponseDto로 수정
                                              @Payload MessageSendRequestDto dto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("clinicId: {}, message: {}", clinicId, dto.getMessage());
        return dto.getMessage();
//        return messageService.saveMessage(clinicId, dto, userDetails);
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
