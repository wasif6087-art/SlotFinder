package com.slotfinder.backend.services;

import com.slotfinder.backend.controllers.AppointmentCheckerController;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import com.slotfinder.backend.models.AppointmentSlot;


@Service
public class EmailService {

    private final AppointmentCheckerController appointmentCheckerController;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender, AppointmentCheckerController appointmentCheckerController) {
        this.javaMailSender = javaMailSender;
        this.appointmentCheckerController = appointmentCheckerController;
    }

public void sendAppointmentNotification(
    String email, 
    AppointmentSlot appointmentSlot
){ 
    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(email);
    message.setSubject("Slot Finder: Appointment Available");
    message.setText(
        "A matching appointment is available!\n\n"
            + "Advsior: " + appointmentSlot.getAdvisorName() + "\n"
            + "Time: " + appointmentSlot.getAppointmentDateTime() + "\n"
            + "Type: " + appointmentSlot.getAppointmentType() + "\n\n"
            + "Please book it as soon as possible."


    );

    javaMailSender.send(message);

}

}

