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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "specialization", nullable = false)
    private String specialization;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(nullable = false)
    private String phoneNumber;
}
