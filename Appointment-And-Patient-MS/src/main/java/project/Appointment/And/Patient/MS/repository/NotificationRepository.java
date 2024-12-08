package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
