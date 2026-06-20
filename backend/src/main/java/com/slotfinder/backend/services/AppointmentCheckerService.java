package com.slotfinder.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.slotfinder.backend.models.AppointmentSlot;

@Service
public class AppointmentCheckerService {

    public List<AppointmentSlot> checkForAppointments() {

        RestTemplate restTemplate = new RestTemplate();

String response =
    restTemplate.getForObject(
        "https://api13.comm100.io/booking/services/b5ae0403bdef420aa87f92b39fa73b7b/availableDatesandtimes?siteId=80000203&timezone=Pacific%20Standard%20Time",
        String.class
    );

System.out.println(response);

        AppointmentSlot slot1 = new AppointmentSlot();

        slot1.setAdvisorName("Andrew");
        slot1.setSource("Comm100");
        slot1.setDetectedAt(java.time.LocalDateTime.now());


        return List.of(slot1);
    }



}
