package project.Appointment.And.Patient.MS.util;

import org.springframework.http.HttpStatus;
import project.Appointment.And.Patient.MS.dto.ApiResponse;

public class ApiResponseBuilder {

    private ApiResponseBuilder() {
    }

    public static <T> ApiResponse<T> buildSuccessResponse(T data, HttpStatus code) {
        return ApiResponse.<T>builder()
                .status("success")
                .data(data)
                .errorDetails(null)
                .code(code)
                .build();
    }

    public static <T> ApiResponse<T> buildErrorResponse(String errorDetails, HttpStatus code) {
        return ApiResponse.<T>builder()
                .status("error")
                .data(null)
                .errorDetails(errorDetails)
                .code(code)
                .build();
    }
}
