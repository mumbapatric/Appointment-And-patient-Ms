package project.Appointment.And.Patient.MS.exceptions;

public class DoctorException {

    public static class DoctorNotFoundException extends RuntimeException {
        public DoctorNotFoundException(String message) {
            super(message);
        }
    }

    public static class DoctorAlreadyExistsException extends RuntimeException {
        public DoctorAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidEmailFormatException extends RuntimeException {
        public InvalidEmailFormatException(String message) {
            super(message);
        }
    }

    public static class NoDoctorsFoundException extends RuntimeException {
        public NoDoctorsFoundException(String message) {
            super(message);
        }
    }
}
