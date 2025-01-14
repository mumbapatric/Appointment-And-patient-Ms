package project.Appointment.And.Patient.MS.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Notification;
import project.Appointment.And.Patient.MS.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;


    public void sendEmail(String to, String subject, String text) {
        emailService.sendEmail(to, subject, text);
    }
    public void sendSms(String to, String message) {
        smsService.sendSms(to, message);
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


