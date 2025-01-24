package org.example.carebridge.domain.clinic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateRequestDto;
import org.example.carebridge.domain.clinic.dto.createclinic.ClinicCreateResponseDto;
import org.example.carebridge.domain.clinic.dto.deletemessage.ClinicDeleteResponseDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendRequestDto;
import org.example.carebridge.domain.clinic.dto.sendmessage.MessageSendResponseDto;
import org.example.carebridge.domain.clinic.service.ClinicService;
import org.example.carebridge.domain.clinic.service.message.MessageService;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clinic")
@RequiredArgsConstructor
@Slf4j
public class ClinicController {

    private final ClinicService clinicService;
    private final MessageService messageService;

    // TODO : valid 추가

    @PostMapping
    public ResponseEntity<ClinicCreateResponseDto> createClinic(@RequestBody ClinicCreateRequestDto dto,
                                                                @AuthenticationPrincipal UserDetailsImple userDetails) {
        return new ResponseEntity<>(clinicService.createClinic(dto, userDetails), HttpStatus.CREATED);
    }

    @MessageMapping("/chat/{clinicId}")
    @SendTo("/sub/chat/{clinicId}")
    public String sendMessage(@DestinationVariable Long clinicId,             // MessageSendResponseDto로 수정
                                              @Payload MessageSendRequestDto dto,
                                              @AuthenticationPrincipal UserDetailsImple userDetails) {
        log.info("clinicId: {}, message: {}", clinicId, dto.getMessage());
        return dto.getMessage();
//        return messageService.saveMessage(chatroomId, dto, userDetails);
    }

    @PostMapping("/{clinicId}")
    public ResponseEntity<ClinicDeleteResponseDto> deleteClinic(@PathVariable Long clinicId,
                                                                @AuthenticationPrincipal UserDetailsImple userDetails) {
        return new ResponseEntity<>(clinicService.deleteClinic(clinicId, userDetails), HttpStatus.OK);
    }
}
