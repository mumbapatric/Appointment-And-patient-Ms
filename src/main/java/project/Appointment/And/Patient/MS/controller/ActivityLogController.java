package project.Appointment.And.Patient.MS.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.dto.AppointmentDTO;
import project.Appointment.And.Patient.MS.repository.ActivityLogRepository;
import project.Appointment.And.Patient.MS.service.ActivityLogService;

@RestController
@RequestMapping("/api/v1/logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;
    private final ActivityLogRepository activityLogRepository;

    public ActivityLogController(ActivityLogService activityLogService,
                                 ActivityLogRepository activityLogRepository) {
        this.activityLogService = activityLogService;
        this.activityLogRepository = activityLogRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, HttpServletRequest request) {
        // Assume login success
        activityLogService.log(username, "Logged in", request);
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/appointments")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointment, HttpServletRequest request) {
        // create appointment logic here (you can call service to save appointment)

        activityLogService.log(getCurrentUser(), "Created an appointment", request);
        return ResponseEntity.ok("Appointment created");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLogs() {
        return ResponseEntity.ok(activityLogRepository.findAll());
    }

    private String getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "anonymousUser";
        }
        return auth.getName();
    }

}
