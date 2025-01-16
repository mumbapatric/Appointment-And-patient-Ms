package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor>findBySpecializationContainingIgnoreCase(String specialization);
    List<Doctor>findByLocationContainingIgnoreCase(String location);
    Optional<Doctor> findByUserId(Long userId);
}

