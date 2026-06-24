package com.slotfinder.backend.models;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class WatchRequest {

    @Email
    @NotBlank
    private String email;
    
    private AppointmentType appointmentType;

    private String advisorPreference;

    private String agentId;

    private Long id;

    private boolean active;

    private LocalDateTime createdAt;

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAdvisorPreference() {
        return advisorPreference;
    }

    public void setAdvisorPreference(String advisorPreference) {
        this.advisorPreference = advisorPreference;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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


