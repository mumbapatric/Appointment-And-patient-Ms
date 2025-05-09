package project.Appointment.And.Patient.MS.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.DoctorRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public CustomUserDetailsService(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElse(null);
        if (doctor != null && doctor.getStatus() == Doctor.Status.FROZEN) {
            throw new UsernameNotFoundException("User account is frozen.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),  // Corrected to getUsername()
                user.getPassword(),  // Stored encoded password
                user.isEnabled(), //fail if false
                true,
                true,
                true,
                user.getAuthorities()  // Using the getAuthorities() method
        );
    }
}
