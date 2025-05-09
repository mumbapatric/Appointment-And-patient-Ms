package project.Appointment.And.Patient.MS.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor>findBySpecializationContainingIgnoreCase(String specialization);
    List<Doctor>findByLocationContainingIgnoreCase(String location);
    Optional<Doctor> findByUserId(Long userId);
    boolean existsByEmail(String email);
    @Transactional
    Optional<Doctor> findByEmail(String email);
    void deleteByEmail(String email);
    List<Doctor>findByNameContainingIgnoreCase(String name);
    List<Doctor> findByStatus(Doctor.Status status);
    Optional<Doctor> findByUserUsername(String username);

}

