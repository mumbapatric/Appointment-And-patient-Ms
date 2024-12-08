package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public Void sendEmail(String to, String subject, String text){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            System.out.println("Email sent to" + to);
        }
        catch (Exception e){
            System.err.println("Error occur in sending email" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
