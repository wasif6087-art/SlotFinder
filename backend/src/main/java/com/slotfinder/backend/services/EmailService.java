package com.slotfinder.backend.services;

import com.slotfinder.backend.controllers.AppointmentCheckerController;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.AppointmentType;

import java.time.format.DateTimeFormatter;


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
    AppointmentSlot appointmentSlot,
    String unsubscribeToken
){ 
    SimpleMailMessage message = new SimpleMailMessage();

    DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a");

    String formattedDateTime = 
        appointmentSlot.getAppointmentDateTime().format(formatter);        

    String formattedAppointmentType;

    if (appointmentSlot.getAppointmentType() == AppointmentType.PHONE_ZOOM){
        formattedAppointmentType = "Phone / Zoom";
    } else {
        formattedAppointmentType = "In-person";
    }

   String unsubscribeLink =
    "http://localhost:5173/unsubscribe/" + unsubscribeToken;

    message.setTo(email);
    message.setSubject("Slot Finder: Appointment Available");
    message.setText(
        "🎓 SlotFinder Appointment Found!\n\n"
            + "To stop monitoring - click here:\n"
            + unsubscribeLink + "\n\n"
            + "Advisor: " + appointmentSlot.getAdvisorName() + "\n\n"
            + "Time: " + formattedDateTime + "\n\n"
            + "Type: " + formattedAppointmentType + "\n\n"
            + "Please book before someone else takes it!\n\n"
            + "- SlotFinder"


    );

    javaMailSender.send(message);

}

}

