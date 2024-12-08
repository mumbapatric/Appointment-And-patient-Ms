package project.Appointment.And.Patient.MS.controller;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    //add appointment
    public ResponseEntity<String> addAppointment(@RequestBody Appointment appointment){
        Appointment appointments = appointmentService.addAppointment(appointment);
        return ResponseEntity.ok("Appointment Added successful");
    }

    //get all
    @GetMapping
    public ResponseEntity<List<Appointment>> findAll(){
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id){
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null){
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    //update Appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id,@RequestBody Appointment appointment){
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        if (updatedAppointment != null){
            return ResponseEntity.ok(updatedAppointment);
        }
        return ResponseEntity.notFound().build();
    }

    //delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id){
        boolean isDeleted = appointmentService.deleteAppointment(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
