package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.Appointment.And.Patient.MS.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);
    List<Appointment> findByDoctor_Id(Long id);
    List<Appointment> findByPatient_id(Long id);
    List<Appointment> findByAppointmentDateTime(LocalDateTime appointmentDateTime);
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findByPatientIdAndDateAfter(Long patientId, LocalDate date);
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}

