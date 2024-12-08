package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Patient;
import project.Appointment.And.Patient.MS.repository.PatientRepository;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //add Patient
    public Patient addPatient(Patient patient){
        return patientRepository.save(patient);
    }

    //find all
    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    //find by id
    public Patient findById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Patient not found" +id));
    }

    //update Patient
    public Patient updatePatient(Long id, Patient updatedPatient){
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("patient not found" + id));
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setName(updatedPatient.getName());

        existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
        return patientRepository.save(existingPatient);
    }

    // delete patient
    public boolean deletePatient(Long id){
        if (patientRepository.existsById(id)){
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
