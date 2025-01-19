package project.Appointment.And.Patient.MS.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterPatientDTO {
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String gender;
    private Long hospitalId;
}
