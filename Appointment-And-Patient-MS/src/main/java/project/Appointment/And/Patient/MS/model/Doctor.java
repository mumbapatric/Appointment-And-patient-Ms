package project.Appointment.And.Patient.MS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String fullName;
    private String specialization;
    private String email;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
