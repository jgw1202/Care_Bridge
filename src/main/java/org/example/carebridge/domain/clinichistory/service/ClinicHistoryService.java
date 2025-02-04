package org.example.carebridge.domain.clinichistory.service;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.clinic.entity.Message;
import org.example.carebridge.domain.clinic.repository.MessageRepository;
import org.example.carebridge.domain.clinic.repository.ParticipationRepository;
import org.example.carebridge.domain.clinichistory.dto.*;
import org.example.carebridge.domain.clinichistory.entity.ClinicHistory;
import org.example.carebridge.domain.clinichistory.repository.ClinicHistoryRepository;
import org.example.carebridge.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicHistoryService {

    private final ClinicHistoryRepository clinicHistoryRepository;
    private final ParticipationRepository participationRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public List<ClinicListResponseDto> getHistoryList(User user) {

        List<ClinicListResponseDto> clinicListResponseDtos = new ArrayList<>();
        Long userId = user.getId();


        //권한에 따른 Dto 분리
        if (user.isDoctor()) {
            List<ClinicHistory> clinicHistories = clinicHistoryRepository.findByDoctorUserId(userId);
            for (ClinicHistory clinicHistory : clinicHistories) {
                clinicListResponseDtos.add(new DoctorClinicListResponseDto(clinicHistory));
            }
            return clinicListResponseDtos;
        } else {
            List<ClinicHistory> clinicHistories = clinicHistoryRepository.findByPatientUserId(userId);
            for (ClinicHistory clinicHistory : clinicHistories) {
                clinicListResponseDtos.add(new PatientClinicListResponseDto(clinicHistory));
            }
            return clinicListResponseDtos;
        }
    }

    @Transactional
    public ClinicResponseDto getHistory(User user, Long clinicId) {

        Long userId = user.getId();

        if(user.isDoctor()) {
            ClinicHistory clinicHistory = clinicHistoryRepository.findByDoctorUserIdAndClinicId(userId, clinicId);

            ClinicResponseDto clinicResponseDto = new ClinicResponseDto(
                    clinicHistory.getId(),
                    clinicHistory.getPaymentStatus(),
                    clinicHistory.getPrescription(),
                    clinicHistory.getModifiedAt(),
                    clinicHistory.getClinic().getId()
            );

            return clinicResponseDto;
        }

        else {
            ClinicHistory clinicHistory = clinicHistoryRepository.findByPatientUserIdAndClinicId(userId, clinicId);

            ClinicResponseDto clinicResponseDto = new ClinicResponseDto(
                    clinicHistory.getId(),
                    clinicHistory.getPaymentStatus(),
                    clinicHistory.getPrescription(),
                    clinicHistory.getModifiedAt(),
                    clinicHistory.getClinic().getId()
            );
            return clinicResponseDto;
        }
    }

    @Transactional
    public List<ClinicMessageResponseDto> getClinicMessage(Long clinicId) {

        List<ClinicMessageResponseDto> messageResponseDtos = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByClinicId(clinicId);

        for (Message message : messages) {
            messageResponseDtos.add(new ClinicMessageResponseDto(message.getSender(), message.getMessageContent()));
        }

        return messageResponseDtos;
    }
}
