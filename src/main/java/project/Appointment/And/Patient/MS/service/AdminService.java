package project.Appointment.And.Patient.MS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.dto.RegisterAdminDTO;
import project.Appointment.And.Patient.MS.model.Admin;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.AdminRepository;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;
    private final UserService UserService;

    public AdminService(project.Appointment.And.Patient.MS.service.UserService userService) {
        UserService = userService;
    }


    public long getTotalAppointments() {
        return appointmentRepository.count();
    }

    public long getTotalRegisteredUsers() {
        return userRepository.count();
    }

    public List<Appointment> getActivityLogs() {
        return appointmentRepository.findAll(); // This should be enhanced to provide actual activity logs
    }

    public Long getTotalPatients() {
        return userRepository.count();
    }

    // create admin
    public Admin addAdmin(RegisterAdminDTO adminDTO){

        User user = new User();
        user.setUsername(adminDTO.getUsername());
        user.setPassword(adminDTO.getPassword());
        user.setPhoneNumber(adminDTO.getPhoneNumber());
        user.setName(adminDTO.getName());
        user.setEmail(adminDTO.getEmail());
        user.setRoles(List.of(User.Role.ADMIN));
        User userCtd = UserService.addUser(user);

        Admin admin = new Admin();
        admin.setUser(userCtd);
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());
        return adminRepository.save(admin);
    }


}
