package project.Appointment.And.Patient.MS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDoctorDTO {
    private String fname;
    private String lname;
    private String username;
    private String specialization;
    private String location;
    private String email;
    private String phonenumber;
    private String password;
}
