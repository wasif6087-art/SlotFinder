package com.slotfinder.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.Notifications;


@Service
public class NotificationsService {

    private List<Notifications> notifications = new ArrayList<>();

    public Notifications createNotification(
        String email, 
        AppointmentSlot appointmentSlot
    ) {
        Notifications notification = new Notifications();
        notification.setEmail(email);
        notification.setAppointmentSlot(appointmentSlot);
        notification.setSentAt(LocalDateTime.now());
        notification.setSent(false);
        notifications.add(notification);
        return notification;

    }

    public List<Notifications> getAllNotifications() {
        return notifications;
    }

}
