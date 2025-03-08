package project.Appointment.And.Patient.MS.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.PasswordResetToken;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.PasswordResetTokenRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;  // Added for encoding passwords

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository,
                                UserRepository userRepository,
                                JavaMailSender javaMailSender,
                                PasswordEncoder passwordEncoder) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void generateToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check if token already exists
        PasswordResetToken resetToken = passwordResetTokenRepository.findByUser(user);
        String token = UUID.randomUUID().toString();

        if (resetToken != null) {
            // Update existing token
            resetToken.setToken(token);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        } else {
            // Create a new token
            resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        }

        passwordResetTokenRepository.save(resetToken);
        sendResetTokenEmail(user.getEmail(), token);
    }

    public void sendResetTokenEmail(String email, String token) {
        String resetLink = "http://localhost:8080/api/users/reset-password?token=" + token;
        String userToken1 =token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("To reset your password, click the link below:\n" + resetLink + "\n\n" + "Your token is: "+ userToken1);
        javaMailSender.send(mailMessage);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired token");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}
