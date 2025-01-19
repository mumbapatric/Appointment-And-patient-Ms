package project.Appointment.And.Patient.MS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;     // "success" or "error"
    private T data;            // Actual data or null for errors
    private String errorDetails;
    private HttpStatus code;
}
