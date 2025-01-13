package project.Appointment.And.Patient.MS.service;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.MedicalRecord;
import project.Appointment.And.Patient.MS.model.Patient;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;
import project.Appointment.And.Patient.MS.repository.MedicalRecordRepository;
import project.Appointment.And.Patient.MS.repository.PatientRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PatientService(PasswordEncoder passwordEncoder, UserRepository userRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, MedicalRecordRepository medicalRecordRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //add Patient
    public Patient addPatient(Patient patient){
        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        User user = new User();
        user.setUsername(patient.getEmail());
        user.setPassword(encodedPassword);
        user.setEmail(patient.getEmail());
        user.setRoles(List.of(User.Role.PATIENT));
        patient.setPassword(encodedPassword);
        userRepository.save(user);
        patient.setUser(user);
        return patientRepository.save(patient);
    }

    //find all
    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    //find by id
    public Patient findById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Patient not found" +id));
    }

    public List<Appointment> getUpcomingAppointments(Long patientId) {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByPatientIdAndDateAfter(patientId, today);
    }


    public List<MedicalRecord> getMedicalHistory(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }


    //update Patient
    public Patient updatePatient(Long id, Patient updatedPatient){
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("patient not found" + id));
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setName(updatedPatient.getName());

        existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
        return patientRepository.save(existingPatient);
    }

    // delete patient
    public boolean deletePatient(Long id){
        if (patientRepository.existsById(id)){
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Patient findPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId).orElse(null);

    }
}
