package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.DoctorHospital;

public interface DoctorHospitalRepository extends JpaRepository<DoctorHospital, Long> {

}
