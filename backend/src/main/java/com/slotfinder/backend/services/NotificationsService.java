package com.slotfinder.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private Set<String> notificationKeys = new HashSet<>();

    public Notification createNotification(
        String email, 
        AppointmentSlot appointmentSlot
    ) {

        String notificationKey = buildNotificationKey(email, appointmentSlot);

        if (notificationKeys.contains(notificationKey)) {
            return null;
        }
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setAppointmentSlot(appointmentSlot);
        notification.setSentAt(LocalDateTime.now());
        notification.setSent(false);
        notifications.add(notification);
        notificationKeys.add(notificationKey);
        emailService.sendAppointmentNotification(email, appointmentSlot);
        return notification;

    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }

    public boolean notificationAlreadyExists(
        String email,
        AppointmentSlot appointmentSlot
    ) {
        String notificationKey = buildNotificationKey(email, appointmentSlot);
        return notificationKeys.contains(notificationKey);
    }

    private String buildNotificationKey(String email, AppointmentSlot appointmentSlot) {
        return email + "|"
                + appointmentSlot.getAdvisorName() + "|"
                + appointmentSlot.getAppointmentDateTime() + "|"
                + appointmentSlot.getAppointmentType();
    }

}
