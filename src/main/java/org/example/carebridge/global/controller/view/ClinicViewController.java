package org.example.carebridge.global.controller.view;

import org.example.carebridge.domain.clinic.entity.Clinic;
import org.example.carebridge.domain.clinic.repository.ClinicRepository;
import org.example.carebridge.domain.clinic.service.ClinicService;
import org.example.carebridge.domain.user.dto.doctor.DoctorListResponseDto;
import org.example.carebridge.domain.user.dto.doctor.DoctorResponseDto;
import org.example.carebridge.domain.user.dto.patient.PatientResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.domain.user.service.UserService;
import org.example.carebridge.global.auth.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClinicViewController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/clinic/popup")
    public String getClinicPopup(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails.getUser().getUserRole().equals(UserRole.USER)) {
            List<DoctorListResponseDto> doctors = userService.getAllDoctor(UserRole.DOCTOR);

            model.addAttribute("doctors", doctors);

            return "clinicpopup-patient";

        } else {
            List<PatientResponseDto> patients =userRepository.findAllByParticipation(userDetails.getUser(), UserRole.USER);

            model.addAttribute("patients", patients);

            return "clinicpopup-doctor";
        }
    }

    @GetMapping("/clinic/popup/{id}")
    public String getDoctorPopup(@PathVariable Long id, Model model) {

        DoctorResponseDto doctor = userService.getDoctor(id, UserRole.DOCTOR);

        model.addAttribute("doctor", doctor);

        return "clinic-patient";
    }

    @GetMapping("/clinic")
    public String getdoctor(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails.getUser().getUserRole().equals(UserRole.USER)) {
            return "clinic-patient";
        } else {
            return "clinic-doctor";
        }
    }
}
