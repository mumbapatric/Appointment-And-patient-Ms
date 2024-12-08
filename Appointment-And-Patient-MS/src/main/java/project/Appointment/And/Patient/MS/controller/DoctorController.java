package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    //add doctor
    @PostMapping
    public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor){
        doctorService.addDoctor(doctor);
        return ResponseEntity.status(201).body("Doctor Added Successful");
    }


    //find all
    @GetMapping
    public ResponseEntity<List<Doctor>> findAll(){
        List<Doctor> doctors = doctorService.findAll();
        return ResponseEntity.ok(doctors);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id){
        Doctor doctor = doctorService.findById(id);
        if (doctor != null){
            return ResponseEntity.ok(doctor);
        }
        return ResponseEntity.notFound().build();
    }

    //update doctor
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id ,@RequestBody Doctor doctor){
        Doctor doctors = doctorService.updateDoctor(id, doctor);
        if (doctors != null){
            return ResponseEntity.ok(doctors);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        boolean isDeleted = doctorService.deleteDoctor(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
