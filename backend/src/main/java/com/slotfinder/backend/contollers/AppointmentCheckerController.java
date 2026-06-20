package com.slotfinder.backend.contollers;

import com.slotfinder.backend.services.AppointmentCheckerService;
import com.slotfinder.backend.models.AppointmentSlot;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentCheckerController {

    private final AppointmentCheckerService appointmentCheckerService;

    public AppointmentCheckerController(AppointmentCheckerService appointmentCheckerService) {
        this.appointmentCheckerService = appointmentCheckerService;
    }

    @GetMapping("/appointments/check")
    public List<AppointmentSlot> checkForAppointments() {
        return appointmentCheckerService.checkForAppointments();
    }

    

}
