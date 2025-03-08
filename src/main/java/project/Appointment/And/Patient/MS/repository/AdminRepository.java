package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
