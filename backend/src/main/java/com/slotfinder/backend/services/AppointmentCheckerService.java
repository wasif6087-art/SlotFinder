package com.slotfinder.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.slotfinder.backend.models.AppointmentSlot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AppointmentCheckerService {

    public List<AppointmentSlot> checkForAppointments() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        List<AppointmentSlot> appointmentSlots = new ArrayList<>();

        String availabilityResponse = restTemplate.getForObject(
                "https://api13.comm100.io/booking/services/b5ae0403bdef420aa87f92b39fa73b7b/availableDatesandtimes?siteId=80000203&timezone=Pacific%20Standard%20Time",
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, List<String>> availabilityMap = objectMapper.readValue(
                availabilityResponse,
                new TypeReference<Map<String, List<String>>>() {}
        );

        for (String date : availabilityMap.keySet()) {

            List<String> times = availabilityMap.get(date);

            for (String time : times) {

                AppointmentSlot slot = new AppointmentSlot();

                slot.setAdvisorName("Unknown");
                slot.setSource("Comm100");
                slot.setDetectedAt(java.time.LocalDateTime.now());

                appointmentSlots.add(slot);
            }
        }

        return appointmentSlots;
    }
}
