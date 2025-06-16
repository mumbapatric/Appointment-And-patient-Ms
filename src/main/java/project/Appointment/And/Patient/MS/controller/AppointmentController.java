package project.Appointment.And.Patient.MS.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.DoctorRepository;
import project.Appointment.And.Patient.MS.service.AppointmentService;
import project.Appointment.And.Patient.MS.service.DoctorService;
import project.Appointment.And.Patient.MS.service.NotificationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;
    private final NotificationService notificationService;
    private final DoctorRepository doctorRepository;

    public AppointmentController(AppointmentService appointmentService,
                                 NotificationService notificationService,
                                 DoctorRepository doctorRepository) {
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
        this.doctorRepository = doctorRepository;
    }

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping
    public ResponseEntity<String> addAppointment(@RequestBody Appointment appointment, HttpServletRequest request) {
        logger.info("Received appointment: {}", appointment);
        Appointment savedAppointment = appointmentService.addAppointment(appointment);
        notificationService.sendEmail(savedAppointment.getPatientEmail(), "Appointment Confirmation", "Your appointment is scheduled.");
        return ResponseEntity.ok("Appointment added successfully");
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> findAll(HttpServletRequest request) {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id, HttpServletRequest request) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment, HttpServletRequest request) {
        Appointment appointment = appointmentService.updateAppointment(id, updatedAppointment);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForPatient(@PathVariable Long patientId, HttpServletRequest request) {
        List<Appointment> appointments = appointmentService.findByPatientName(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForDoctor(@PathVariable Long doctorId, HttpServletRequest request) {
        List<Appointment> appointments = appointmentService.findByDoctor(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<Appointment> rescheduleAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment, HttpServletRequest request) {
        Appointment rescheduledAppointment = appointmentService.updateAppointment(id, updatedAppointment);
        return ResponseEntity.ok(rescheduledAppointment);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id, HttpServletRequest request) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, Appointment.AppointmentStatus.CANCEL,null, null);
        if (updatedAppointment != null) {
            notificationService.sendEmail(updatedAppointment.getPatientEmail(), "Appointment Cancelled", "Your appointment has been cancelled.");
            return ResponseEntity.ok("Appointment cancelled successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<String> confirmAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment, HttpServletRequest request) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            if (updatedAppointment.getDate() != null) {
                appointment.setDate(updatedAppointment.getDate());
            }
            if (updatedAppointment.getTime() != null) {
                appointment.setTime(updatedAppointment.getTime());
            }
            appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
            appointmentService.save(appointment);
            notificationService.sendSms(
                    appointment.getPatient().getPhoneNumber(),
                    "Appointment Confirmed: Your appointment has been confirmed for " +
                            appointment.getDate() + " at " + appointment.getTime() + "."
            );
            notificationService.sendEmail(
                    appointment.getPatientEmail(),
                    "Appointment Confirmed",
                    "Your appointment has been confirmed for " + appointment.getDate() + " at " + appointment.getTime() + "."
            );
            return ResponseEntity.ok("Appointment confirmed successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectAppointment(@PathVariable Long id, HttpServletRequest request) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, Appointment.AppointmentStatus.CANCEL,null, null);
        if (updatedAppointment != null) {
            notificationService.sendEmail(updatedAppointment.getPatientEmail(), "Appointment Rejected", "Your appointment has been rejected by the doctor.");
            return ResponseEntity.ok("Appointment rejected successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id, HttpServletRequest request) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            notificationService.sendEmail(appointment.getPatient().getEmail(), "Appointment Cancellation", "An appointment has been cancelled.");
            notificationService.sendEmail(appointment.getDoctor().getEmail(), "Appointment Cancellation", "An appointment has been cancelled.");
            boolean isDeleted = appointmentService.deleteAppointment(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/report")
    public ResponseEntity<?> generateAppointmentReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("format") String format,
            HttpServletRequest request
    ) throws IOException {
        byte[] report = appointmentService.generateAppointmentReport(startDate, endDate, format);

        String contentType = format.equalsIgnoreCase("pdf") ? "application/pdf" : "application/vnd.ms-excel";
        String fileName = "appointments_report." + format;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType(contentType))
                .body(report);
    }
}
