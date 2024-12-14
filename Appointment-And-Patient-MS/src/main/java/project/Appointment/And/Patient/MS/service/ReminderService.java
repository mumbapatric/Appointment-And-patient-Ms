package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Scheduled(cron = "0 * * * * ?") // Run every minute
    public void sendReminders() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateTimeBetween(startOfDay, endOfDay);
        for (Appointment appointment : appointments) {
            String patientPhone = appointment.getPatientPhoneNumber();
            if (patientPhone != null && !patientPhone.isEmpty()) {
                notificationService.sendSms(patientPhone, "Reminder: You have an appointment today.");
            } else {
                // Log the issue for debugging purposes
                System.err.println("Patient phone number is null or empty for appointment ID: " + appointment.getId());
            }
            notificationService.sendEmail(appointment.getPatientEmail(), "Appointment Reminder", "Reminder: You have an appointment today.");
        }
    }
}
