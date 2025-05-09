package project.Appointment.And.Patient.MS.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.dto.ApiResponse;
import project.Appointment.And.Patient.MS.dto.RegisterDoctorDTO;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.service.DoctorService;
import project.Appointment.And.Patient.MS.util.ApiResponseBuilder;

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
    public ResponseEntity<ApiResponse<Doctor>> addDoctor(@RequestBody RegisterDoctorDTO doctor) {
        Doctor doctorCreated = doctorService.addDoctor(doctor);
        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                doctorCreated,
                HttpStatus.CREATED
        ));
    }

    @GetMapping("/dashboard/daily-schedule/{doctorId}")
    public ResponseEntity<List<Appointment>> getDailySchedule(@PathVariable Long doctorId) {
        List<Appointment> appointments = doctorService.getDailySchedule(doctorId);
        return appointments.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(appointments);
    }

    //find all
    @GetMapping
    public ResponseEntity<ApiResponse<List<Doctor>>> findAll() {
        List<Doctor> doctors = doctorService.findAll();

        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                doctors,
                HttpStatus.OK
        ));
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Doctor>> findById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);

        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                doctor,
                HttpStatus.OK
        ));
    }

    //find doctor by name
    @GetMapping("/doctorName")
    public ResponseEntity<List<Doctor>> findByName(@RequestParam String name) {
        List<Doctor> doctors = doctorService.findDoctorByName(name);
        return doctors.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(doctors);
    }

    //find doctor by specialization
    @GetMapping("/specialization")
    public ResponseEntity<List<Doctor>> findBySpecialization(@RequestParam String query) {
        List<Doctor> doctors = doctorService.findBySpecialization(query);
        return doctors.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(doctors);
    }

//get doctor by user id
    @GetMapping("/user/{userId}")
    public Doctor getDoctorByUserId(@PathVariable Long userId) {
        return doctorService.getDoctorByUserId(userId);
    }


    //update doctor
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<Void> deleteDoctor(@RequestParam String email) {
        boolean isDeleted = doctorService.deleteDoctor(email);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{doctorId}/freeze")
    public ResponseEntity<Void> freezeDoctor(@PathVariable Long doctorId) {
        doctorService.freezeDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{doctorId}/unfreeze")
    public ResponseEntity<Void> unfreezeDoctor(@PathVariable Long doctorId){
        doctorService.unFreezeDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }

}
