package project.Appointment.And.Patient.MS.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.ActivityLog;
import project.Appointment.And.Patient.MS.repository.ActivityLogRepository;

import java.time.LocalDateTime;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void log(String username, String action, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        ActivityLog logEntry = new ActivityLog();
        logEntry.setUsername(username);
        logEntry.setAction(action);
        logEntry.setIpAddress(ip);
        logEntry.setTimestamp(LocalDateTime.now());

        activityLogRepository.save(logEntry);

        // Optional: also print to console or logs
        System.out.printf("User [%s] performed action: %s from IP: %s%n", username, action, ip);
    }
}
