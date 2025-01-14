package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
}
