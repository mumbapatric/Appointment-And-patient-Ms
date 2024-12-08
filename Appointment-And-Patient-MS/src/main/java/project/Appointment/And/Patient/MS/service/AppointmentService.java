package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    //add appointment
    public Appointment addAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    // find all
    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    //find by id
    public Appointment findById(Long id){
        return appointmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Appointment not found " + id));
    }

    // find appointment by date
    public List<Appointment> findByDate(LocalDate date){
        return appointmentRepository.findByDate(date);
    }

    // find by status
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        // If status is null or invalid, handle it
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }

        return appointmentRepository.findByStatus(status);
    }


    //find by patient name
    public List<Appointment> findByPatientName(String name){
      return   appointmentRepository.findByPatient_NameContainingIgnoreCase(name);
    }

    //find by doctor
    public List<Appointment> findByDoctor(String name){
        return appointmentRepository.findByDoctor_NameContainingIgnoreCase(name);
    }

    // update Appointment

    public Appointment updateAppointment(Long id , Appointment updatedAppointment){
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("appointment not found" + id));
        existingAppointment.setAppointmentDateTime(updatedAppointment.getAppointmentDateTime());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setSchedule(updatedAppointment.getSchedule());
        existingAppointment.setLocation(updatedAppointment.getLocation());
        existingAppointment.setStatus(updatedAppointment.getStatus());
        existingAppointment.setDate(updatedAppointment.getDate());
        return appointmentRepository.save(existingAppointment);
    }

    // delete appointment
    public boolean deleteAppointment(Long id){
        if (appointmentRepository.existsById(id)){
            appointmentRepository.deleteById(id);
            return true;
        }
        return  false;
    }

}
