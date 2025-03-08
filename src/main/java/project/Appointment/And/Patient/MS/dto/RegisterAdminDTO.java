package project.Appointment.And.Patient.MS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

}
