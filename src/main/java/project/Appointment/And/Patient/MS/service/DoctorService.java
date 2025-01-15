package project.Appointment.And.Patient.MS.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.exceptions.DoctorException;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;
import project.Appointment.And.Patient.MS.repository.DoctorRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         DoctorRepository doctorRepository,
                         AppointmentRepository appointmentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //add doctor
    public Doctor addDoctor(Doctor doctor) {
        // Check if email already exists for a doctor
        doctorRepository.findByEmail(doctor.getEmail())
                .ifPresent(existingDoctor -> {
                    throw new DoctorException.DoctorAlreadyExistsException("Email already exists: " + doctor.getEmail());
                });

        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        User user = new User();
        user.setUsername(doctor.getEmail());
        user.setPassword(encodedPassword);
        user.setEmail(doctor.getEmail());
        user.setRoles(List.of(User.Role.DOCTOR));

        userRepository.save(user);
        doctor.setPassword(encodedPassword);
        doctor.setUser(user);

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

        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        existingDoctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        existingDoctor.setEmail(updatedDoctor.getEmail());

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
