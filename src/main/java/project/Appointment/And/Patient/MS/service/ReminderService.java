package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Scheduled(cron = "0 * * * * ?") // Run every minute
    public void sendReminders() {
        // Get the current time and calculate the 15-minute window
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusMinutes(15);

        // Find appointments that are scheduled for 15 minutes from now
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateTime(reminderTime);

        // Send reminders to each appointment
        for (Appointment appointment : appointments) {
            // Send reminder to the patient
            String patientPhone = appointment.getPatientPhoneNumber();
            if (patientPhone != null && !patientPhone.isEmpty()) {
                notificationService.sendSms(patientPhone, "Reminder: You have an appointment in 15 minutes with" + appointment.getDoctor().getUser().getName() + ".");
            } else {
                // Log the issue for debugging purposes
                System.err.println("Patient phone number is null or empty for appointment ID: " + appointment.getId());
            }
            notificationService.sendEmail(appointment.getPatientEmail(), "Appointment Reminder", "Reminder: You have an appointment in 15 minutes with" + appointment.getDoctor().getUser().getName() + ".");

            // Send reminder to the doctor
            String doctorPhone = appointment.getDoctor().getUser().getPhoneNumber();
            if (doctorPhone != null && !doctorPhone.isEmpty()) {
                notificationService.sendSms(doctorPhone, "Reminder: You have an appointment in 15 minutes with patient " + appointment.getPatient().getName() + ".");
            } else {
                // Log the issue for debugging purposes
                System.err.println("Doctor phone number is null or empty for appointment ID: " + appointment.getId());
            }
            notificationService.sendEmail(appointment.getDoctor().getUser().getEmail(), "Appointment Reminder", "Reminder: You have an appointment in 15 minutes with patient " + appointment.getPatient().getName() + ".");
        }
    }
}
