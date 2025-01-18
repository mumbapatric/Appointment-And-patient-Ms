package project.Appointment.And.Patient.MS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDoctorDTO {
    private String name;
    private String username;
    private String specialization;
    private String location;
    private String email;
    private String phoneNumber;
    private String password;
    private Long hospitalId;
}
