package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
