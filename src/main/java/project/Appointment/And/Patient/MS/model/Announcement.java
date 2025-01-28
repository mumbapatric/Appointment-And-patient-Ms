package project.Appointment.And.Patient.MS.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Announcement {
    private Long id;
    private String title;
    private String message;
}
