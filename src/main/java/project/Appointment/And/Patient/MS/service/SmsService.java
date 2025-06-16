package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {

    @Value("${nextsms.username}")
    private String username;

    @Value("${nextsms.password}")
    private String password;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendSms(String to, String messageText) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Phone number must not be empty.");
        }

        String formattedTo = formatPhoneNumber(to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String credentials = username + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(credentials.getBytes());
        headers.set("Authorization", "Basic " + base64Creds);

        Map<String, Object> body = new HashMap<>();
        body.put("from", "BCL");
        body.put("to", formattedTo);
        body.put("text", messageText);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        String url = "https://messaging-service.co.tz/api/sms/v1/text/single";

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            System.err.println("Failed to send SMS: " + response.getBody());
            throw new RuntimeException("Failed to send SMS: " + response.getBody());
        }

        System.out.println("SMS sent successfully: " + response.getBody());
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            phoneNumber = phoneNumber.substring(1);
        }
        return "+255" + phoneNumber;
    }
}
