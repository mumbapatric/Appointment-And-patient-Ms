package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.MedicalRecord;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;
import project.Appointment.And.Patient.MS.repository.MedicalRecordRepository;

import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, AppointmentRepository appointmentRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //TODO method to check if doctor is authorized
    private boolean isDoctorAuthorized(Long doctorId, Long patientId) {
        return appointmentRepository.existsByDoctorIdAndPatientIdAndStatus(
                doctorId,
                patientId,
                Appointment.AppointmentStatus.CONFIRMED
        );
    }
    // Add medical record
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        Long doctorId = medicalRecord.getDoctor().getId();
        Long patientId = medicalRecord.getPatient().getId();

        // Authorization check based on appointment
        if (!isDoctorAuthorized(doctorId, patientId)) {
            throw new RuntimeException("Doctor is not authorized to add a medical record for this patient.");
        }
        return medicalRecordRepository.save(medicalRecord);
    }


    //find by patient id
    public List<MedicalRecord> findByPatientId(Long id){
        return medicalRecordRepository.findByPatientId(id);
    }

    //find all medical record
    public List<MedicalRecord> findAll(){
       return medicalRecordRepository.findAll();
    }

    public MedicalRecord findById(Long id){
        return medicalRecordRepository.findById(id)
                .orElseThrow(()->new RuntimeException("medical record not found" + id));
    }

    // Update medical record
    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecordDetails) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with ID " + id));

        Long doctorId = medicalRecordDetails.getDoctor().getId();
        Long patientId = medicalRecordDetails.getPatient().getId();

        // Authorization check based on appointment
        if (!isDoctorAuthorized(doctorId, patientId)) {
            throw new RuntimeException("Doctor is not authorized to update this medical record.");
        }

        medicalRecord.setRecordDate(medicalRecordDetails.getRecordDate());
        medicalRecord.setNotes(medicalRecordDetails.getNotes());
        medicalRecord.setPatient(medicalRecordDetails.getPatient());
        medicalRecord.setDoctor(medicalRecordDetails.getDoctor());

        return medicalRecordRepository.save(medicalRecord);
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
