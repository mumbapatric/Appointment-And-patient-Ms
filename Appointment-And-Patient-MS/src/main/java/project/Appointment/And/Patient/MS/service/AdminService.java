package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public long getTotalAppointments() {
        return appointmentRepository.count();
    }

    public long getTotalRegisteredUsers() {
        return userRepository.count();
    }

    public List<Appointment> getActivityLogs() {
        return appointmentRepository.findAll(); // This should be enhanced to provide actual activity logs
    }
}
