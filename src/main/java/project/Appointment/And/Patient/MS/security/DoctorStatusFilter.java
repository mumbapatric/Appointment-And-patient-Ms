package project.Appointment.And.Patient.MS.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.repository.DoctorRepository;

import java.io.IOException;
import java.util.Optional;

@Component
public class DoctorStatusFilter extends OncePerRequestFilter {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Check if the principal represents a User
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User user =
                        (org.springframework.security.core.userdetails.User) principal;

                // Check if the user is associated with a frozen doctor
                Optional<Doctor> doctor = doctorRepository.findByUserUsername(user.getUsername());
                if (doctor.isPresent() && doctor.get().getStatus() == Doctor.Status.FROZEN) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403 Forbidden
                    response.getWriter().write("Access denied: doctor account is frozen.");
                    return;
                }
            }
        }

        // Proceed with the filter chain if not blocked
        filterChain.doFilter(request, response);
    }
}