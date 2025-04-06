package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Announcement;
import project.Appointment.And.Patient.MS.repository.AnnouncementRepository;

import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository repository;

    public Announcement createAnnouncement(String message) {
        Announcement a = new Announcement();
        a.setMessage(message);
        a.setActive(true);
        return repository.save(a);
    }

    public Optional<Announcement> getLatestActive() {
        return repository.findTopByIsActiveTrueOrderByCreatedAtDesc();
    }
}
