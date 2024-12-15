package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Add appointment
    public Appointment addAppointment(Appointment appointment) {
        appointment.setStatus(Appointment.AppointmentStatus.PENDING); // Set initial status to PENDING
        return appointmentRepository.save(appointment);
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
        return appointmentRepository.findByDoctor_Id(id);
    }

    // Update appointment
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found " + id));
        existingAppointment.setAppointmentDateTime(updatedAppointment.getAppointmentDateTime());
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
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return appointmentRepository.findByAppointmentDateTimeBetween(startOfDay, endOfDay);
    }

    // Update appointment status
    public Appointment updateAppointmentStatus(Long id, Appointment.AppointmentStatus status) {
        Appointment appointment = findById(id);
        if (appointment != null) {
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
}
