package project.Appointment.And.Patient.MS.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DoctorHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToMany
    @JoinColumn(name = "doctor_id", nullable = false)
    private  Doctor doctor;
    @ManyToMany
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
}
