package project.Appointment.And.Patient.MS.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.dto.RegisterDoctorDTO;
import project.Appointment.And.Patient.MS.exceptions.DoctorException;
import project.Appointment.And.Patient.MS.exceptions.UserException;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;
import project.Appointment.And.Patient.MS.repository.DoctorRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;


    //add doctor
    public Doctor addDoctor(RegisterDoctorDTO userDoctor) {

        User user = new User();
        user.setUsername(userDoctor.getUsername());
        user.setPassword(userDoctor.getPassword());
        user.setEmail(userDoctor.getEmail());
        user.setRoles(List.of(User.Role.DOCTOR));
        User userCtd = userService.addUser(user);

        // set user doctor
        Doctor doctor = new Doctor();
        doctor.setUser(userCtd);
        doctor.setSpecialization(userDoctor.getSpecialization());
        doctor.setLocation(userDoctor.getLocation());
        doctor.setPhoneNumber(userDoctor.getPhonenumber());
        // other doctor specific fields

        return doctorRepository.save(doctor);
    }

    //find all doctor
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    // find by id
    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorException.DoctorNotFoundException("Doctor not found with ID " + id));
    }

    //find doctor by user id
    public Doctor getDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorException.DoctorNotFoundException("Doctor not found for user ID: " + userId));
    }

    // find doctor by location name
    public List<Doctor> findDoctorByLocation(String query) {
        return doctorRepository.findByLocationContainingIgnoreCase(query);
    }

    public List<Appointment> getDailySchedule(Long doctorId) {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByDoctorIdAndDate(doctorId, today);
    }

    // find by specialization
    public List<Doctor> findBySpecialization(String query) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(query);
    }

    //update doctor
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorException.DoctorNotFoundException("Doctor not found with ID " + id));

        existingDoctor.getUser().setName(updatedDoctor.getUser().getName());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        existingDoctor.getUser().setPhonenumber(updatedDoctor.getUser().getPhonenumber());
        existingDoctor.getUser().setEmail(updatedDoctor.getUser().getEmail());

        return doctorRepository.save(existingDoctor);
    }

    //delete doctor
    public boolean deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorException.DoctorNotFoundException("Doctor not found with ID " + id);
        }
        doctorRepository.deleteById(id);
        return true;
    }

    public Doctor findDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorException.DoctorNotFoundException("Doctor not found for user ID: " + userId));
    }
}
