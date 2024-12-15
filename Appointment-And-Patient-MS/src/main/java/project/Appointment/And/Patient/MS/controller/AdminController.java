package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard/total-appointments")
    public ResponseEntity<Long> getTotalAppointments() {
        return ResponseEntity.ok(adminService.getTotalAppointments());
    }

    @GetMapping("/dashboard/total-users")
    public ResponseEntity<Long> getTotalRegisteredUsers() {
        return ResponseEntity.ok(adminService.getTotalRegisteredUsers());
    }

    @GetMapping("/dashboard/activity-logs")
    public ResponseEntity<List<Appointment>> getActivityLogs() {
        return ResponseEntity.ok(adminService.getActivityLogs());
    }
}
