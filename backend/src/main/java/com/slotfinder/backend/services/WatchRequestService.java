package com.slotfinder.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.WatchRequest;

import com.slotfinder.backend.repositories.WatchRequestRepository;

@Service
public class WatchRequestService {

    private final AppointmentCheckerService appointmentCheckerService;
    private final ServiceAgentService serviceAgentService;
    private final WatchRequestRepository watchRequestRepository;

    public WatchRequestService(
            WatchRequestRepository watchRequestRepository,
            AppointmentCheckerService appointmentCheckerService, 
            ServiceAgentService serviceAgentService
    ) {
        this.watchRequestRepository = watchRequestRepository;
        this.appointmentCheckerService = appointmentCheckerService;
        this.serviceAgentService = serviceAgentService;
    }

    public String createWatchRequest(WatchRequest request) {
      
        request.setActive(true);
        watchRequestRepository.save(request);

        return "SERVICE: Watch request received for " + request.getEmail();
    }

    public List<WatchRequest> getAllWatchRequests() {
        return watchRequestRepository.findAll();
    } 

    public List<WatchRequest> getActiveWatchRequests() {
        return watchRequestRepository.findByActiveTrue();
    }

    public String cancelWatchRequest(Long id) //This method is used for Swagger Manual Testing
     {
        WatchRequest request = watchRequestRepository.findById(id).orElse(null);

        if (request ==  null) {
            return "No watch request found";
        }

        request.setActive(false);
        watchRequestRepository.save(request);

        return "Watch request has been cancelled for " + request.getEmail();
    }

    public String cancelWatchRequest(String unsubscribeToken) {

        WatchRequest request = watchRequestRepository
                .findByUnsubscribeToken(unsubscribeToken)
                .orElse(null);

        if (request == null) {
            return "No watch request found";
        }

        request.setActive(false);
        watchRequestRepository.save(request);

        return "Watch request has been cancelled for " + request.getEmail();
    }












    public List<AppointmentSlot> findMatches(Long id) throws Exception {
        WatchRequest request = watchRequestRepository.findById(id).orElse(null);

        if (request ==  null) {
            return List.of();
        }

        return findMatches(request);
    }

    public List<AppointmentSlot> findMatches(WatchRequest request) throws Exception {
        if (request.getAgentId() != null &&
                !request.getAgentId().isBlank()) {
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
