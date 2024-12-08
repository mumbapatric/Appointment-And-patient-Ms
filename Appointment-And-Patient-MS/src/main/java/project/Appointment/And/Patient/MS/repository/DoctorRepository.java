package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor>findBySpecializationContainingIgnoreCase(String query);
    List<Doctor>findByLocationContainingIgnoreCase(String query);
}
