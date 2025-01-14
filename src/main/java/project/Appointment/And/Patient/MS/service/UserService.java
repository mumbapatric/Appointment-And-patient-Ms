package project.Appointment.And.Patient.MS.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername()); }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail()); }
        return userRepository.save(user);
    }

    //find all
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //find user by id
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException ("User not found with id " + id));
    }

    //update user
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    //delete user
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // delete user
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new NoSuchElementException("User not found with username: " + username)); }
}
