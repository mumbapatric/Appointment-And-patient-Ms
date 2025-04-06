package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByOrderByCreatedAtDesc();
    Optional<Announcement> findTopByIsActiveTrueOrderByCreatedAtDesc();
}
