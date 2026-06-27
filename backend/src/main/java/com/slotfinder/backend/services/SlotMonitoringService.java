package com.slotfinder.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.slotfinder.backend.models.WatchRequest;
import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.Notification;

@Service
public class SlotMonitoringService {

    private final WatchRequestService watchRequestService;
    private final NotificationsService notificationsService;

    public SlotMonitoringService(
            WatchRequestService watchRequestService, 
            NotificationsService notificationsService
    ) {
        this.watchRequestService = watchRequestService;
        this.notificationsService = notificationsService;
    }

    public void checkAllWatchRequests() throws Exception {
        List<WatchRequest> watchRequests = watchRequestService.getAllWatchRequests();

        for (WatchRequest request : watchRequests) {
            List<AppointmentSlot> matches = watchRequestService.findMatches(request);

            for (AppointmentSlot appointmentSlot : matches) {

                Notification notification =
                    notificationsService.createNotification(
                            request.getEmail(),
                            appointmentSlot
                );

                System.out.println("Notification created for " + notification.getEmail());
            }
        }
    }
}
