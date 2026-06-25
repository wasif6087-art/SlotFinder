package com.slotfinder.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slotfinder.backend.services.SlotMonitoringService;

@RestController
public class SlotMonitoringController {

    private final SlotMonitoringService slotMonitoringService;

    public SlotMonitoringController(SlotMonitoringService slotMonitoringService) {
        this.slotMonitoringService = slotMonitoringService;
    }

    @GetMapping("/monitor/check")
    public String checkAllWatchRequests() throws Exception {
        slotMonitoringService.checkAllWatchRequests();

        return "Monitoring check completed";
    }
}