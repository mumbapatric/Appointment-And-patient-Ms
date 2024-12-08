package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Notification;
import project.Appointment.And.Patient.MS.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //add notification
    @PostMapping
    public ResponseEntity<String> addNotification(@RequestBody Notification notification){
        Notification notifications = notificationService.addNotification(notification);
        return ResponseEntity.status(201).body("notification Added Successful");
    }

    //get all
    @GetMapping
    public ResponseEntity<List<Notification>> findAll(){
        List<Notification> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    //find By id
    @GetMapping("{id}")
public ResponseEntity<Notification> findById(@PathVariable Long id){
        Notification notification = notificationService.findById(id);
        if (notification != null){
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.notFound().build();
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification){
        Notification updatedNotification = notificationService.updateNotification(id, notification);
        if (updatedNotification != null){
            return ResponseEntity.ok(updatedNotification);
        }
        return ResponseEntity.notFound().build();
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id){
        boolean isDeleted =notificationService.deleteNotification(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
