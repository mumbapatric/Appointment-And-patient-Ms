package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);
    List<Appointment> findByDoctor_NameContainingIgnoreCase(String name);
    List<Appointment> findByPatient_NameContainingIgnoreCase(String name);
}

