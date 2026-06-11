package com.slotfinder.backend.models;

public class WatchRequest {

    private String email;
    
    private String appointmentType;

    private String advisorPreference;

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAdvisorPreference() {
        return advisorPreference;
    }

    public void setAdvisorPreference(String advisorPreference) {
        this.advisorPreference = advisorPreference;
    }

    }


