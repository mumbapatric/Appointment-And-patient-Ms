package project.Appointment.And.Patient.MS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;


}
