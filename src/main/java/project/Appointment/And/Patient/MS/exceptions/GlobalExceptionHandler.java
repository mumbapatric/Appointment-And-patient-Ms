package project.Appointment.And.Patient.MS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.Appointment.And.Patient.MS.dto.ApiResponse;
import project.Appointment.And.Patient.MS.util.ApiResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- User Exception Handlers ---
    @ExceptionHandler(UserException.UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserException.UserNotFoundException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserException.UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameAlreadyExistsException(UserException.UsernameAlreadyExistsException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserException.EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExistsException(UserException.EmailAlreadyExistsException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserException.InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordException(UserException.InvalidPasswordException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // --- Doctor Exception Handlers ---
    @ExceptionHandler(DoctorException.DoctorNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDoctorNotFoundException(DoctorException.DoctorNotFoundException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DoctorException.DoctorAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleDoctorAlreadyExistsException(DoctorException.DoctorAlreadyExistsException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DoctorException.InvalidEmailFormatException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidDoctorEmailFormatException(DoctorException.InvalidEmailFormatException ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DoctorException.NoDoctorsFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoDoctorEmailFormatException(DoctorException.NoDoctorsFoundException ex) {
        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // --- General Exception Handler (if no matching Exception) ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {

        ApiResponse<Void> errorResponse = ApiResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
