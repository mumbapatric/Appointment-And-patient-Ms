package project.Appointment.And.Patient.MS.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    // Constructor Injection for dependencies
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    //add doctor
    @PostMapping
    public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor added successfully");
    }

    @GetMapping("/dashboard/daily-schedule/{doctorId}")
    public ResponseEntity<List<Appointment>> getDailySchedule(@PathVariable Long doctorId) {
        List<Appointment> appointments = doctorService.getDailySchedule(doctorId);
        return appointments.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(appointments);
    }

    //find all
    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        return doctors.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(doctors);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(doctor);
    }

    //find doctor by specialization
    @GetMapping("/specialization")
    public ResponseEntity<List<Doctor>> findBySpecialization(@RequestParam String query) {
        List<Doctor> doctors = doctorService.findBySpecialization(query);
        return doctors.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(doctors);
    }

    //update doctor
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        boolean isDeleted = doctorService.deleteDoctor(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
