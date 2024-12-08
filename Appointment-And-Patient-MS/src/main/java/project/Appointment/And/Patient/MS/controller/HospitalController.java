package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Hospital;
import project.Appointment.And.Patient.MS.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    //add hospital
    @PostMapping
    public ResponseEntity<String> addHospital(@RequestBody Hospital hospital){
        Hospital hospitals = hospitalService.addHospital(hospital);
        return ResponseEntity.status(201).body("hospital added successful!");
    }

    //get all
    @GetMapping
    public ResponseEntity<List<Hospital>> findAll(){
        List<Hospital> hospitals = hospitalService.findAll();
        return ResponseEntity.ok(hospitals);
    }

    //get By id
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> findById(@PathVariable Long id){
        Hospital hospital = hospitalService.findById(id);
       if (hospital != null){
           return ResponseEntity.ok(hospital);
       }
       return ResponseEntity.notFound().build();
    }

    //update hospitals
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestBody Hospital hospital){
        Hospital updatedHospital = hospitalService.updateHospital(id, hospital);
        if (updatedHospital != null){
            return ResponseEntity.ok(updatedHospital);
        }
        return ResponseEntity.notFound().build();
    }

    // delete hospital
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id){
        boolean isDeleted = hospitalService.deleteHospital(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
