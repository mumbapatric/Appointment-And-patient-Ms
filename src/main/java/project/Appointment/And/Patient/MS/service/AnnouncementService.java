package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Announcement;
import project.Appointment.And.Patient.MS.repository.AnnouncementRepository;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    // Admin creates an announcement
    public Announcement createAnnouncement(String title, String message) {
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setMessage(message);
        return announcementRepository.save(announcement);
    }

    // Get all announcements (visible to all users)
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findByOrderByCreatedAtDesc();
    }
}
