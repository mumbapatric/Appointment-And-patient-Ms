package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Announcement;
import project.Appointment.And.Patient.MS.repository.AnnouncementRepository;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

import java.util.List;

@Service
public class AnnouncementService {
   private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    //post announcement
    public Announcement postAnnouncement(Announcement announcement){
        return announcementRepository.save(announcement);
    }

    //get all
    public List<Announcement> findAll(){
        return announcementRepository.findAll();
    }

}
