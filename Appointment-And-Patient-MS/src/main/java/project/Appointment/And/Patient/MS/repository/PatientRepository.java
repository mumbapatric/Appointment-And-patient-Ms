package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {


}
