package project.Appointment.And.Patient.MS.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.exceptions.UserException;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // add user to Db
    public User addUser(User user) {
        // Use orElseThrow to avoid unnecessary ifChecks for username and email existence
        userRepository.findByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    throw new UserException.UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserException.EmailAlreadyExistsException("Email already exists: " + user.getEmail());
                });

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

        // Use Optional to check password and avoid redundant ifCheck
        Optional.ofNullable(updatedUser.getPassword())
                .filter(password -> !password.isEmpty())
                .ifPresentOrElse(
                        password -> existingUser.setPassword(passwordEncoder.encode(password)),
                        () -> {
                            throw new UserException.InvalidPasswordException("Password cannot be null or empty.");
                        }
                );

        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
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
}
