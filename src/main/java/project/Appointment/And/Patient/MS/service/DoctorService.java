package project.Appointment.And.Patient.MS.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.dto.RegisterDoctorDTO;
import project.Appointment.And.Patient.MS.exceptions.DoctorException;
import project.Appointment.And.Patient.MS.model.*;
import project.Appointment.And.Patient.MS.repository.*;

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
    private final HospitalRepository hospitalRepository;
    private final DoctorHospitalRepository doctorHospitalRepository;
    
    //add doctor
    public Doctor addDoctor(RegisterDoctorDTO userDoctor) {

        User user = new User();
        user.setUsername(userDoctor.getUsername());
        user.setPassword(userDoctor.getPassword());
        user.setPhoneNumber(userDoctor.getPhoneNumber());
        user.setName(userDoctor.getName());
        user.setEmail(userDoctor.getEmail());
        user.setRoles(List.of(User.Role.DOCTOR));
        User userCtd = userService.addUser(user);

        // set user doctor
        Doctor doctor = new Doctor();
        doctor.setUser(userCtd);
        doctor.setSpecialization(userDoctor.getSpecialization());
        doctor.setLocation(userDoctor.getLocation());
        doctor.setPhoneNumber(userDoctor.getPhoneNumber());
        doctor.setName(userDoctor.getName());
        doctor.setEmail(userDoctor.getEmail());
        Doctor savedDoctor = doctorRepository.save(doctor);
        // other doctor specific fields

        //doctor hospital association
        Hospital hospital = hospitalRepository.findById(userDoctor.getHospitalId())
                .orElseThrow(()-> new RuntimeException("hospital id not found"));

        DoctorHospital doctorHospital = new DoctorHospital();
        doctorHospital.setDoctor(savedDoctor);
        doctorHospital.setHospital(hospital);
        doctorHospitalRepository.save(doctorHospital);

        return savedDoctor;
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
        existingDoctor.getUser().setPhoneNumber(updatedDoctor.getUser().getPhoneNumber());
        existingDoctor.getUser().setEmail(updatedDoctor.getUser().getEmail());

        return doctorRepository.save(existingDoctor);
    }

    //find doctor by name
    public List<Doctor> findDoctorByName(String query){
        return doctorRepository.findByNameContainingIgnoreCase(query);
    }

    //delete doctor
    public boolean deleteDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorException.DoctorNotFoundException("Doctor not found with email " + email));
        appointmentRepository.deleteByDoctorId(doctor.getId());
        doctorHospitalRepository.deleteByDoctorId(doctor.getId());
        userRepository.deleteByDoctorId(doctor.getId());
        doctorRepository.delete(doctor);
        return true;
    }


    public Doctor findDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorException.DoctorNotFoundException("Doctor not found for user ID: " + userId));
    }

    public Long getTotalDoctors() {
        return doctorRepository.count();
    }

    //freeze doctor
    public Doctor freezeDoctor(Long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()->new RuntimeException("Doctor not found with id: " + id));
        doctor.setStatus(Doctor.Status.FROZEN);
        doctor.getUser().setEnabled(false);
        return doctorRepository.save(doctor);
    }

    //unfreeze doctor
    public Doctor unFreezeDoctor(Long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()->new RuntimeException("Doctor not found with id: " + id));
        doctor.setStatus(Doctor.Status.ACTIVE);
        doctor.getUser().setEnabled(true);
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findAllActiveDoctors() {
        return doctorRepository.findByStatus(Doctor.Status.ACTIVE);
    }
    public List<Doctor> findAllFrozenDoctors() {
        return doctorRepository.findByStatus(Doctor.Status.FROZEN);
    }
}
