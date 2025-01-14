package project.Appointment.And.Patient.MS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.Appointment.And.Patient.MS.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- User Exception Handlers ---
    @ExceptionHandler(UserException.UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserException.UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "User Not Found",
                ex.getMessage(),
                "User with the specified ID does not exist."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UserException.UsernameAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Username Conflict",
                ex.getMessage(),
                "A user with the given username already exists."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(UserException.EmailAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Email Conflict",
                ex.getMessage(),
                "A user with the given email already exists."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(UserException.InvalidPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Password",
                ex.getMessage(),
                "The provided password is invalid."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // --- Doctor Exception Handlers ---
    @ExceptionHandler(DoctorException.DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDoctorNotFoundException(DoctorException.DoctorNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Doctor Not Found",
                ex.getMessage(),
                "Doctor with the specified ID does not exist."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorException.DoctorAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDoctorAlreadyExistsException(DoctorException.DoctorAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Doctor Conflict",
                ex.getMessage(),
                "A doctor with the given email already exists."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorException.InvalidEmailFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDoctorEmailFormatException(DoctorException.InvalidEmailFormatException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Doctor Email Format",
                ex.getMessage(),
                "The provided email format for the doctor is invalid."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // --- General Exception Handler (if no matching Exception) ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                "An unexpected error occurred."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
