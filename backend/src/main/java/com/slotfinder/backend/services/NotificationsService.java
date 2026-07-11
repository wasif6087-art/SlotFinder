package com.slotfinder.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.Notification;
import com.slotfinder.backend.repositories.NotificationsRepository;

@Service
public class NotificationsService {

    private final EmailService emailService;
    private final NotificationsRepository notificationsRepository;

    public NotificationsService(
        EmailService emailService, 
        NotificationsRepository notificationsRepository) {
        this.emailService = emailService;
        this.notificationsRepository = notificationsRepository;
    }

    
  

    public Notification createNotification(
        String email, 
        AppointmentSlot appointmentSlot
    ) {

        String notificationKey = buildNotificationKey(email, appointmentSlot);

        if (notificationAlreadyExists(email, appointmentSlot)) {
            return null;
        }
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setAppointmentSlot(appointmentSlot);
        notification.setSentAt(LocalDateTime.now());
        notification.setSent(false);
        notification.setNotificationKey(notificationKey);
        notificationsRepository.save(notification);
        emailService.sendAppointmentNotification(email, appointmentSlot);
        return notification;

    }

    public List<Notification> getAllNotifications() {
        return notificationsRepository.findAll();
    }

    public boolean notificationAlreadyExists(
        String email,
        AppointmentSlot appointmentSlot
    ) {
        String notificationKey = buildNotificationKey(email, appointmentSlot);
        return notificationsRepository.existsByNotificationKey(notificationKey);
    }

    private String buildNotificationKey(
        String email, 
        AppointmentSlot appointmentSlot
    ) {
        return email + "|"
                + appointmentSlot.getAdvisorName() + "|"
                + appointmentSlot.getAppointmentDateTime() + "|"
                + appointmentSlot.getAppointmentType();
    }

}
