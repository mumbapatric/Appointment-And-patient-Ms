package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Notification;
import project.Appointment.And.Patient.MS.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // add notification
    public Notification  addNotification(Notification notification){
        return notificationRepository.save(notification);
    }

    //find all
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    //find by id
    public Notification findById(Long id){
        return notificationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("notification not found" + id));
    }

    //update
    public Notification updateNotification(Long id, Notification updatedNotification){
        Notification notification = notificationRepository.findById(id).orElse(null);
        notification.setStatus(updatedNotification.getStatus());
        notification.setMessage(updatedNotification.getMessage());
        notification.setSentAt(updatedNotification.getSentAt());
        return notificationRepository.save(notification);
    }

    //delete notification
    public boolean deleteNotification(Long id){
        if (notificationRepository.existsById(id)){
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
