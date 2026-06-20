package com.slotfinder.backend.services;

import org.springframework.stereotype.Service;

import java.util.List;

import com.slotfinder.backend.models.AppointmentSlot;

@Service
public class AppointmentCheckerService {

    public List<AppointmentSlot> checkForAppointments() {

        AppointmentSlot slot1 = new AppointmentSlot();

        slot1.setAdvisorName("Andrew");
        slot1.setSource("Comm100");
        slot1.setDetectedAt(java.time.LocalDateTime.now());


        return List.of(slot1);
    }

}
