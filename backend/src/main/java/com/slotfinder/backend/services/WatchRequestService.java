package com.slotfinder.backend.services;
import com.slotfinder.backend.models.WatchRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.slotfinder.backend.models.AppointmentType;
import com.slotfinder.backend.models.AppointmentSlot;

@Service
public class WatchRequestService {

    private List<WatchRequest> watchRequests = new ArrayList<>();
    private Long nextId = 1L;
    private final AppointmentCheckerService appointmentCheckerService;
    private final ServiceAgentService serviceAgentService;

    public WatchRequestService(
        AppointmentCheckerService appointmentCheckerService, 
        ServiceAgentService serviceAgentService
    ) {
        this.appointmentCheckerService = appointmentCheckerService;
        this.serviceAgentService = serviceAgentService;
    }
    

    public String createWatchRequest(WatchRequest request) {

        request.setId(nextId);
        nextId++;

        request.setActive(true);

        request.setCreatedAt(LocalDateTime.now());


        watchRequests.add(request);

        return "SERVICE: Watch request received for " + request.getEmail();
    }

    public List<WatchRequest> getAllWatchRequests() {
        return watchRequests;
    } 

    public String cancelWatchRequest(Long id) {
        for (WatchRequest request : watchRequests) {
            if (request.getId().equals(id)) {
                request.setActive(false);
                return "Watch request has been cacelled for " + request.getEmail();
            } 
        }

    
        return "No watch request found";
    }

    public List<AppointmentSlot> findMatches(Long id) throws Exception {
        for (WatchRequest request : watchRequests) {
            if (request.getId().equals(id)) {
                return findMatches(request);
            }
        }

        return new ArrayList<>();
    }

    public List<AppointmentSlot> findMatches(WatchRequest request) throws Exception {
        if (request.getAgentId() != null) {
            String advisorName = serviceAgentService.getAdvisorNameByAgentId(
                    request.getAgentId()
            );

            return appointmentCheckerService.checkForAppointmentsByAdvisor(
                    request.getAgentId(),
                    advisorName,
                    request.getAppointmentType()
            );
        }

        return appointmentCheckerService.checkForAppointments(
                request.getAppointmentType()
        );
    }

}
