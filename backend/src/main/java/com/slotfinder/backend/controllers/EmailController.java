package com.slotfinder.backend.controllers;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.AppointmentType;
import com.slotfinder.backend.services.EmailService;
import com.slotfinder.backend.services.NotificationsService;

// Temporary test controller.
// Delete this controller before production.
@RestController
public class EmailController {

    private final EmailService emailService;
    private final NotificationsService notificationsService;

    public EmailController(
            EmailService emailService,
            NotificationsService notificationsService) {

        this.emailService = emailService;
        this.notificationsService = notificationsService;
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

    @GetMapping("/notifications/test")
    public String createTestNotification(@RequestParam String email) {

        AppointmentSlot slot = new AppointmentSlot();
        slot.setAdvisorName("Dr. Smith");
        slot.setAppointmentDateTime(LocalDateTime.of(
            2026, 7, 15, 10, 0)
        );
        slot.setAppointmentType(AppointmentType.PHONE_ZOOM);

        if (notificationsService.createNotification(email, slot) == null) {
            return "Duplicate notification detected.";
        }

        return "Notification created successfully!";
    }
}