package com.slotfinder.backend.models;
import java.time.LocalDateTime;

public class WatchRequest {

    private String email;
    
    private String appointmentType;

    private String advisorPreference;

    private Long id;

    private boolean active;

    private LocalDateTime createdAt;

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

    public void setId(Long id) {
        this.id = id;

    }
    
    public Long getId() {
        return id;
    }

    public void setActive(boolean active) {
        this.active = active;

    }

    public boolean getActive() {
        return active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}


