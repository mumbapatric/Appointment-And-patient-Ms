package project.Appointment.And.Patient.MS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;     // "success" or "error"
    private String message;    // General message (success or error message)
    private T data;            // Actual data or null for errors
    private String errorDetails; // Additional error details (null for success)
    private int code;          // HTTP status code (e.g., 200, 404, etc.)

//    public ResponseEntity<T> getResponse( ){
//        return ApiResponse.<T>builder()
//                .status("success")
//                .message(this.message)
//                .data(this.data)
//                .errorDetails(this.errorDetails)
//                .code(this.code)
//                .build().getResponse();
//    }
}
