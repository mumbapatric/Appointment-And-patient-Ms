package project.Appointment.And.Patient.MS.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.service.AppointmentService;
import project.Appointment.And.Patient.MS.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private NotificationService notificationService;

    // Add appointment
    @PostMapping
    public ResponseEntity<String> addAppointment(@RequestBody Appointment appointment) {
        logger.info("Received appointment: {}", appointment);
        Appointment savedAppointment = appointmentService.addAppointment(appointment);

        notificationService.sendSms(savedAppointment.getPatientPhoneNumber(), "Your appointment is confirmed.");
        notificationService.sendEmail(savedAppointment.getPatientEmail(), "Appointment Confirmation", "Your appointment is confirmed.");
        return ResponseEntity.ok("Appointment added successfully");
    }

    // Get all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    // Get appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    // Confirm appointment
    @PutMapping("/{id}/confirm")
    public ResponseEntity<String> confirmAppointment(@PathVariable Long id) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, Appointment.AppointmentStatus.CONFIRMED);
        if (updatedAppointment != null) {
            notificationService.sendSms(updatedAppointment.getPatientPhoneNumber(), "Your appointment has been confirmed by the doctor.");
            notificationService.sendEmail(updatedAppointment.getPatientEmail(), "Appointment Confirmed", "Your appointment has been confirmed by the doctor.");
            return ResponseEntity.ok("Appointment confirmed successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // Cancel appointment
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, Appointment.AppointmentStatus.CANCEL);
        if (updatedAppointment != null) {
            notificationService.sendSms(updatedAppointment.getPatientPhoneNumber(), "Your appointment has been cancelled by the doctor.");
            notificationService.sendEmail(updatedAppointment.getPatientEmail(), "Appointment Cancelled", "Your appointment has been cancelled by the doctor.");
            return ResponseEntity.ok("Appointment cancelled successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            notificationService.sendSms(appointment.getDoctor().getPhoneNumber(), "An appointment has been cancelled.");
            notificationService.sendEmail(appointment.getDoctor().getEmail(), "Appointment Cancellation", "An appointment has been cancelled.");

            boolean isDeleted = appointmentService.deleteAppointment(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
