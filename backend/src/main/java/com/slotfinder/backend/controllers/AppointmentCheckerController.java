package com.slotfinder.backend.controllers;

import com.slotfinder.backend.services.AppointmentCheckerService;
import com.slotfinder.backend.models.AppointmentSlot;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;

import com.slotfinder.backend.services.ServiceAgentService;

import com.slotfinder.backend.models.AppointmentType;

@RestController
public class AppointmentCheckerController {

    private final AppointmentCheckerService appointmentCheckerService;
    private final ServiceAgentService serviceAgentService;

    
    public AppointmentCheckerController(
        AppointmentCheckerService appointmentCheckerService,
        ServiceAgentService serviceAgentService
    ) {
        this.appointmentCheckerService = appointmentCheckerService;
        this.serviceAgentService = serviceAgentService;
    }

    @GetMapping("/appointments/check")
    public List<AppointmentSlot> checkForAppointments(
        @RequestParam AppointmentType appointmentType
    ) throws Exception {
        return appointmentCheckerService.checkForAppointments(appointmentType);
    }

    @GetMapping("/appointments/check/advisor")
    public List<AppointmentSlot> checkForAppointmentsByAdvisor(
            @RequestParam String agentId,
            @RequestParam AppointmentType appointmentType
    ) throws Exception {
        String advisorName = serviceAgentService.getAdvisorNameByAgentId(agentId);
        return appointmentCheckerService.checkForAppointmentsByAdvisor(
                agentId,
                advisorName,
                appointmentType
        );
    }


    

}
