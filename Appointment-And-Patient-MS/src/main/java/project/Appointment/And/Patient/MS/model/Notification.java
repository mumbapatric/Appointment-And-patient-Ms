package project.Appointment.And.Patient.MS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
    private LocalDateTime sentAt;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    public enum NotificationStatus {
        SENT,
        READ,
        DELIVERED
    }
}
