
package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.model.Patient;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.report.ExcelReportGenerator;
import project.Appointment.And.Patient.MS.report.PdfReportGenerator;
import project.Appointment.And.Patient.MS.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final EmailService emailService;
    private final PdfReportGenerator pdfReportGenerator;
    private final ExcelReportGenerator excelReportGenerator;

    public AppointmentService(NotificationRepository notificationRepository,  UserRepository userRepository, NotificationService notificationService, PatientRepository patientRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, EmailService emailService, PdfReportGenerator pdfReportGenerator, ExcelReportGenerator excelReportGenerator) {
        this.userRepository = userRepository;

        this.notificationService = notificationService;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.emailService = emailService;
        this.pdfReportGenerator = pdfReportGenerator;
        this.excelReportGenerator = excelReportGenerator;
    }

    public Appointment addAppointment(Appointment appointment) {
        // Load patient details
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Patient phone number must not be null or empty");
        }

        // Load doctor details
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        if (doctor.getStatus() == Doctor.Status.FROZEN){
            throw new IllegalArgumentException("Appointment cannot be created. The doctor is frozen.\n");
        }

        // Set patient and doctor details
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);

        // Save appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Construct the notification message
        String notificationMessage = "Your appointment is scheduled with Dr. " +
                savedAppointment.getDoctor().getUser().getName() +
                " on " + savedAppointment.getTime() + savedAppointment.getDate() +
                " at " + savedAppointment.getLocation();

        String notificationDoctor = "Dear Doctor you have appointment with" +   savedAppointment
                .getPatient().getName()  +  " on " + savedAppointment.getTime() +savedAppointment.getDate() +
                " at " + savedAppointment.getLocation() + "kindly check schedule to confirm or cancel";

        // Send SMS notification
       // notificationService.sendSms(patient.getPhoneNumber(), notificationMessage);
      //  notificationService.sendSms(doctor.getUser().getPhoneNumber(), notificationDoctor);
        // Send Email notification
        emailService.sendEmail(patient.getEmail(),
                "Appointment Confirmation", notificationMessage);
        emailService.sendEmail(doctor.getUser().getEmail(),"Appointment Confirmation"
                ,notificationDoctor);

        return savedAppointment;
    }



    // Find all
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    // Find by ID
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found " + id));
    }

    // Find appointment by date
    public List<Appointment> findByDate(LocalDate date) {
        return appointmentRepository.findByDate(date);
    }

    // Find by status
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        return appointmentRepository.findByStatus(status);
    }

    // Find by patient id
    public List<Appointment> findByPatientName(Long id) {
        return appointmentRepository.findByPatient_id(id);
    }

    // Find by doctor
    public List<Appointment> findByDoctor(Long id) {
        return appointmentRepository.findByDoctorId(id);
    }

    // Update appointment
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found " + id));
        existingAppointment.setTime(updatedAppointment.getTime());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setSchedule(updatedAppointment.getSchedule());
        existingAppointment.setLocation(updatedAppointment.getLocation());
        existingAppointment.setStatus(updatedAppointment.getStatus());
        existingAppointment.setDate(updatedAppointment.getDate());
        return appointmentRepository.save(existingAppointment);
    }

    // Delete appointment
    public boolean deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Find appointments for today
    public List<Appointment> findAppointmentsForToday() {
        LocalTime startOfDay = LocalTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return appointmentRepository.findByTimeBetween(startOfDay, endOfDay);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Update appointment status
    public Appointment updateAppointmentStatus(Long id, Appointment.AppointmentStatus status,LocalDate date,LocalTime time) {
        Appointment appointment = findById(id);
        if (appointment != null) {
            appointment.setStatus(status);
            if (date != null) appointment.setDate(date);
            if (time != null) appointment.setTime(time);
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    public byte[] generateAppointmentReport(LocalDate startDate, LocalDate endDate, String format) throws IOException {
        List<Appointment>appointments = appointmentRepository.findByDateBetween(startDate, endDate);
        List<Map<String, Object>> data = appointments.stream().map(a -> Map.of(
                "Patient Username",(Object) a.getPatient().getUser().getUsername(),
                "Patient Name",(Object) a.getPatient().getName(),
                "Doctor Name",(Object) a.getDoctor().getName(),
                "Date",(Object) a.getDate().toString(),
                "Status",(Object) a.getStatus()
        )).toList();

        if (format.equalsIgnoreCase("pdf")) {
            return pdfReportGenerator.generate(data, "Appointments Report");
        } else {
            return excelReportGenerator.generate(data, "Appointments Report");
        }
    }
}
