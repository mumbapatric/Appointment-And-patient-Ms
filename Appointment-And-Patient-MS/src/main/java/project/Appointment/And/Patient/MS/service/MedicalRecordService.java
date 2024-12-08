package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.MedicalRecord;
import project.Appointment.And.Patient.MS.repository.MedicalRecordRepository;

import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    //add medical report
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);

    }

    //find all medical record
    public List<MedicalRecord> findAll(){
       return medicalRecordRepository.findAll();
    }

    public MedicalRecord findById(Long id){
        return medicalRecordRepository.findById(id)
                .orElseThrow(()->new RuntimeException("medical record not found" + id));
    }

    //update
    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecordDetails) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElse(null);
        if (medicalRecord != null) {
            medicalRecord.setRecordDate(medicalRecordDetails.getRecordDate());
            medicalRecord.setNotes(medicalRecordDetails.getNotes());
            medicalRecord.setPatient(medicalRecordDetails.getPatient());
            medicalRecord.setDoctor(medicalRecordDetails.getDoctor());
            return medicalRecordRepository.save(medicalRecord);
        }
        return null;
    }

    //delete
    public boolean deleteMedicalRecord(Long id){
        if (medicalRecordRepository.existsById(id)){
            medicalRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
