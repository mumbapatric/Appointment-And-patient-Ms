package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.MedicalRecord;
import project.Appointment.And.Patient.MS.model.Patient;
import project.Appointment.And.Patient.MS.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    //add patient
    @PostMapping
    public ResponseEntity<String> createPatient(@RequestBody Patient patient) {
        Patient patients = patientService.addPatient(patient);
        return ResponseEntity.status(201).body("patient added successful");
    }

    @GetMapping("/dashboard/upcoming-appointments/{patientId}")
    public ResponseEntity<List<Appointment>> getUpcomingAppointments(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getUpcomingAppointments(patientId)); }

    @GetMapping("/dashboard/medical-history/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getMedicalHistory(patientId)); }

    // get all
    @GetMapping
    public ResponseEntity<List<Patient>> findAll() {
        List<Patient> patients = patientService.findAll();
        return ResponseEntity.ok(patients);
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient patients = patientService.updatePatient(id, patient);
        if (patients != null) {
            return ResponseEntity.ok(patients);
        }
        return ResponseEntity.notFound().build();
    }

    //delete
    @DeleteMapping("/id")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        boolean isDeleted = patientService.deletePatient(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

