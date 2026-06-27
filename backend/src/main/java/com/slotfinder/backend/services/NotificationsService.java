package com.slotfinder.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.Notification;


@Service
public class NotificationsService {

    private final EmailService emailService;

    public NotificationsService(EmailService emailService) {
        this.emailService = emailService;
    }

    private List<Notification> notifications = new ArrayList<>();

    public Notification createNotification(
        String email, 
        AppointmentSlot appointmentSlot
    ) {
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setAppointmentSlot(appointmentSlot);
        notification.setSentAt(LocalDateTime.now());
        notification.setSent(false);
        notifications.add(notification);
        emailService.sendAppointmentNotification(email, appointmentSlot);
        return notification;

    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }

}

