package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDateContainingIgnoreCase(String query);
    List<Appointment>findByStatusContainingIgnoreCase(String query);
    List<Appointment>findByDoctorContainingIgnoreCase(String query);
    List<Appointment> findByPatientContainingIgnoreCase(String query);
}
