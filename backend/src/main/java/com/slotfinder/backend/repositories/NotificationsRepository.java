package com.slotfinder.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.slotfinder.backend.models.Notification;



public interface NotificationsRepository extends JpaRepository<Notification, Long> {

    boolean existsByNotificationKey(String notificationKey);
}
