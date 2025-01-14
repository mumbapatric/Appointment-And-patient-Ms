package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.model.Patient;
import project.Appointment.And.Patient.MS.repository.DoctorRepository;
import project.Appointment.And.Patient.MS.repository.PatientRepository;
import project.Appointment.And.Patient.MS.security.JwtService;

import java.util.Optional;

@Service
public class UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    @Autowired
    private JwtService jwtService;

    // Method to get details of the currently logged-in user
    public Object getCurrentUserDetails(String token) {
        Long userId = jwtService.extractUserId(token);

        // Check if the user is a Patient
        Optional<Patient> patient = patientRepository.findByUserId(userId);
        if (patient.isPresent()) {
            return patient.get(); // Return Patient details
        }

        // Check if the user is a Doctor
        Optional<Doctor> doctor = doctorRepository.findByUserId(userId);
        if (doctor.isPresent()) {
            return doctor.get(); // Return Doctor details
        }


        // If no details are found, throw an error
        throw new IllegalArgumentException("User details not found for userId: " + userId);
    }
}
