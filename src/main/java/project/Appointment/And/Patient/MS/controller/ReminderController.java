package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.service.ReminderService;

@RestController
@RequestMapping("/api/v1/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping("/send")
    public ResponseEntity<String> sendReminders() {
        reminderService.sendReminders();
        return ResponseEntity.ok("Reminders sent successfully");
    }
}
