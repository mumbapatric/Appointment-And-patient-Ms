package project.Appointment.And.Patient.MS.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

   @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    @Bean
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
