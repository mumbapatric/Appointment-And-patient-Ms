package project.Appointment.And.Patient.MS.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment {
    private Long id;
    private LocalDateTime appointmentDateTime;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private LocalDate date;
    private String location;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCEL
    }
}
