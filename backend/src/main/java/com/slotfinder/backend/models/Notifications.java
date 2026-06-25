package com.slotfinder.backend.models;
import java.time.LocalDateTime;


public class Notifications {

    private String email;

    private AppointmentSlot appointmentSlot;

    private LocalDateTime sentAt;

    private boolean sent;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppointmentSlot getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(AppointmentSlot appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    

}
