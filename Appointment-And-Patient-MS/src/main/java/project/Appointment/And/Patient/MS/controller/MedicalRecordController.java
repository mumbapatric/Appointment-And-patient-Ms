package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.MedicalRecord;
import project.Appointment.And.Patient.MS.service.MedicalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicalRecords")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    // add records
    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        MedicalRecord medicalRecords = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.status(201).body("medical record added successful");
    }

    //get all
    @GetMapping
    public ResponseEntity<List<MedicalRecord>> findAll(){
        List<MedicalRecord> medicalRecords = medicalRecordService.findAll();
        return ResponseEntity.ok(medicalRecords);
    }

    //get all id
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> findById(@PathVariable Long id){
        MedicalRecord medicalRecord = medicalRecordService.findById(id);
        if (medicalRecord != null){
            return ResponseEntity.ok(medicalRecord);
        }
        return ResponseEntity.notFound().build();

    }
    // update
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord){
        MedicalRecord medicalRecords = medicalRecordService.updateMedicalRecord(id, medicalRecord);
        if (medicalRecords != null){
            return ResponseEntity.ok(medicalRecords);
        }

        return ResponseEntity.notFound().build();
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id){
        boolean isDeleted = medicalRecordService.deleteMedicalRecord(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
