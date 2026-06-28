package com.slotfinder.backend.controllers;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.AppointmentType;
import com.slotfinder.backend.services.EmailService;

//Temporary test controller to test email sending functionality. 
// This will be removed later.

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("JUST FOR TESTING - delete later")
    public String sendTestEmail(@RequestParam String email) {

        AppointmentSlot slot = new AppointmentSlot();
        slot.setAdvisorName("Dr. Smith");
        slot.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        slot.setAppointmentType(AppointmentType.PHONE_ZOOM);

        emailService.sendAppointmentNotification(email, slot);

        return "Test email sent!";
    }
}