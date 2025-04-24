package project.Appointment.And.Patient.MS.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderService {

    private final NotificationService notificationService;
    private final AppointmentRepository appointmentRepository;

    public ReminderService(NotificationService notificationService, AppointmentRepository appointmentRepository) {
        this.notificationService = notificationService;
        this.appointmentRepository = appointmentRepository;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void sendReminders() {
        // Get the current date
        LocalDate today = LocalDate.now();
        // Calculate time 15 minutes from now
        LocalTime reminderTime = LocalDateTime.now().plusMinutes(15).toLocalTime();
        // Define a 1-minute range around the reminder time
        LocalTime startTime = reminderTime.minusMinutes(1);
        LocalTime endTime = reminderTime.plusMinutes(1);

        System.out.println("Reminder Date: " + today);
        System.out.println("Reminder Time Range: " + startTime + " to " + endTime);

        // Query appointments by date, time range, and CONFIRMED status
        List<Appointment> appointments = appointmentRepository.findByDateAndTimeBetweenAndStatus(
                today,
                startTime,
                endTime,
                Appointment.AppointmentStatus.CONFIRMED
        );

        if (appointments.isEmpty()) {
            System.out.println("No confirmed appointments found for date: " + today + " and time: " + reminderTime);
            return; // Exit if no appointments were found
        }

        // Send reminders for each appointment
        for (Appointment appointment : appointments) {
            String patientName = appointment.getPatient() != null ? appointment.getPatient().getName() : "Patient";
            String patientPhone = appointment.getPatientPhoneNumber();
            String doctorName = appointment.getDoctorName();

            // Send reminders to patients
          /*  if (patientPhone != null && !patientPhone.isEmpty()) {
                notificationService.sendSms(patientPhone, "Hi " + patientName + ", you have an appointment in 15 minutes with Dr. " + doctorName + ".");
            }*/
            notificationService.sendEmail(appointment.getPatientEmail(),
                    "Appointment Reminder",
                    "Hi " + patientName + ", you have an appointment in 15 minutes with Dr. " + doctorName + ".");

            // Send reminders to doctors
            String doctorPhone = (appointment.getDoctor() != null && appointment.getDoctor().getUser() != null) ?
                    appointment.getDoctor().getUser().getPhoneNumber() : null;

          /*  if (doctorPhone != null && !doctorPhone.isEmpty()) {
                notificationService.sendSms(doctorPhone, "Dr. " + doctorName + ", you have an appointment in 15 minutes with patient " + patientName + ".");
            }*/
            notificationService.sendEmail(appointment.getDoctor().getUser().getEmail(),
                    "Appointment Reminder",
                    "Dr. " + doctorName + ", you have an appointment in 15 minutes with patient " + patientName + ".");
        }
    }
}