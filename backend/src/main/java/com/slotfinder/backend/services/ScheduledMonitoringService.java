package com.slotfinder.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class ScheduledMonitoringService {

    private final SlotMonitoringService slotMonitoringService;

    public ScheduledMonitoringService(
            SlotMonitoringService slotMonitoringService
    ) {
        this.slotMonitoringService = slotMonitoringService;
    }

    @Scheduled(fixedRate = 60000)
    public void monitoringAppointments() throws Exception {
        slotMonitoringService.checkAllWatchRequests();
    }


}

