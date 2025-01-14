package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
