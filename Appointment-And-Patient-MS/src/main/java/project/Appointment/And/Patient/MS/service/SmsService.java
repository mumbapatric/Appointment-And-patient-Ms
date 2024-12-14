package project.Appointment.And.Patient.MS.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendSms(String to, String message) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("The 'to' phone number must not be null or empty.");
        }
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                message)
                .create();
    }
}
