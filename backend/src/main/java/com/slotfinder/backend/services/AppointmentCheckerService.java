package com.slotfinder.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.AppointmentType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Service
public class AppointmentCheckerService {

    private String getServiceId(AppointmentType appointmentType) {
        if (appointmentType == AppointmentType.PHONE_ZOOM) {
            return "b5ae0403bdef420aa87f92b39fa73b7b";
        }

        if (appointmentType == AppointmentType.IN_PERSON) {
            return "c6d022ad2a8243d2a4e2e79429681e78";
        }

        throw new IllegalArgumentException("Unsupported appointment type: " + appointmentType);
    }

    public List<AppointmentSlot> checkForAppointments(AppointmentType appointmentType) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        List<AppointmentSlot> appointmentSlots = new ArrayList<>();

        String serviceId = getServiceId(appointmentType);

      
        URI availabilityUri = URI.create(
                "https://api13.comm100.io/booking/services/" + serviceId + "/availableDatesandtimes"
                        + "?siteId=80000203"
                        + "&timezone=Pacific%20Standard%20Time"
        );
        

        String availabilityResponse = restTemplate.getForObject(
                availabilityUri,
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

                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("yyyy-MM-dd h:mm a")
                        .toFormatter(Locale.ENGLISH);

                LocalDateTime appointmentDateTime = LocalDateTime.parse(date + " " + time, formatter);

                slot.setAdvisorName("Unknown");
                slot.setAppointmentDateTime(appointmentDateTime);
                slot.setSource("Comm100");
                slot.setDetectedAt(java.time.LocalDateTime.now());
                slot.setAppointmentType(appointmentType);
                appointmentSlots.add(slot);
            }
        }

        return appointmentSlots;
    }

    public List<AppointmentSlot> checkForAppointmentsByAdvisor(
            String agentId,
            String advisorName,
            AppointmentType appointmentType
    ) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        List<AppointmentSlot> appointmentSlots = new ArrayList<>();

        String serviceId = getServiceId(appointmentType);

        URI availabilityUri = URI.create(
               "https://api13.comm100.io/booking/services/" + serviceId + "/availableDatesandtimes"
                    + "?agentId=" + agentId
                    + "&siteId=80000203"
                    + "&timezone=Pacific%20Standard%20Time"
        );

        String availabilityResponse = restTemplate.getForObject(
                availabilityUri,
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

                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("yyyy-MM-dd h:mm a")
                        .toFormatter(Locale.ENGLISH);

                LocalDateTime appointmentDateTime = LocalDateTime.parse(date + " " + time, formatter);

                slot.setAdvisorName(advisorName);
                slot.setAppointmentDateTime(appointmentDateTime);
                slot.setSource("Comm100");
                slot.setDetectedAt(java.time.LocalDateTime.now());
                slot.setAppointmentType(appointmentType);

                appointmentSlots.add(slot);
            }
        }

        return appointmentSlots;
    }
}
