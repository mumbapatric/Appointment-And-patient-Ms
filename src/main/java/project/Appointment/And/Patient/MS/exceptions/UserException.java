package project.Appointment.And.Patient.MS.exceptions;

public class UserException {

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UsernameAlreadyExistsException extends RuntimeException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    public static class NoUsersFoundException extends RuntimeException {
        public NoUsersFoundException(String message) {
            super(message);
        }
    }
}
