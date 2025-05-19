package project.Appointment.And.Patient.MS.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.dto.ChangePassword;
import project.Appointment.And.Patient.MS.exceptions.UserException;
import project.Appointment.And.Patient.MS.model.Patient;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.PatientRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    // Create a logger instance
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepository;
    }

    // add user to Db
    public User addUser(User user) {
        // Use orElseThrow to avoid unnecessary ifChecks for username and email existence
        log.info("++++++++++++++++++++++++++ CHECK USERNAME ++++++++++++++++++++++++");
        userRepository.findByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    throw new UserException.UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
                });

        log.info("++++++++++++++++++++++++++ CHECK EMAIL ++++++++++++++++++++++++");
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserException.EmailAlreadyExistsException("Email already exists: " + user.getEmail());
                });

        log.info("++++++++++++++++++++++++++ SAVE PASSWORD ++++++++++++++++++++++++");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    //find all
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //find user by id
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found with id " + id));
    }

    //update user
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found with id " + id));
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        User savedUser = userRepository.save(existingUser);
        Patient patient = patientRepository.findByUserId(savedUser.getId()).orElse(null);
        if (patient != null) {
            patient.setEmail(savedUser.getEmail());
            patient.setPhoneNumber(savedUser.getPhoneNumber());
            patientRepository.save(patient);
        }
        return savedUser;
    }


    //delete user
    public boolean deleteUser(Long id) {
        // Use orElseThrow to avoid manual ifCheck for existence
        userRepository.findById(id)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found with id " + id));

        userRepository.deleteById(id);
        return true;
    }

    // delete user
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found with username: " + username));
    }

    public User changePassword(Long id, ChangePassword request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + id));

        if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            return userRepository.save(user);
        }

        throw new RuntimeException("Old password is incorrect");
    }

}
