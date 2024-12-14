package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.Appointment.And.Patient.MS.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);
    List<Appointment> findByDoctor_NameContainingIgnoreCase(String name);
    List<Appointment> findByPatient_NameContainingIgnoreCase(String name);

    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}

