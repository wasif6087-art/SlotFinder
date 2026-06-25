package com.slotfinder.backend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slotfinder.backend.models.Notifications;
import com.slotfinder.backend.services.NotificationsService;

@RestController
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @GetMapping("/notifications")
    public List<Notifications> getAllNotifications() {
        return notificationsService.getAllNotifications();
    }
}