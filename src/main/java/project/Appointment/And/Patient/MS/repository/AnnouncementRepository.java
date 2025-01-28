package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
}
