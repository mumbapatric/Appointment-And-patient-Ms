package project.Appointment.And.Patient.MS.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.model.User.Role;
import project.Appointment.And.Patient.MS.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            // Get authenticated user's details
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Find user ID using email
            User user = userService.findByUsername(userDetails.getUsername());

            // Generate JWT with user details and user ID
            String jwt = jwtService.generateToken(userDetails, user.getId());

            // Return the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(AuthenticationResponse.builder()
                    .token(jwt)
                    .role(userDetails.getAuthorities().iterator().next().getAuthority()) // Assuming single role
                    .build(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Ensure the user has at least one role
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return new ResponseEntity<>("User must have at least one role", HttpStatus.BAD_REQUEST);
        }
        userService.addUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("User registered successfully", headers, HttpStatus.OK);
    }
}
