package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.repository.AppointmentRepository;

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
    public List<Appointment> findByDate(String query){
        return appointmentRepository.findByDateContainingIgnoreCase(query);
    }

    // find by status
    public List<Appointment> findByStatus(String query){
        return appointmentRepository.findByStatusContainingIgnoreCase(query);
    }

    //find by patient name
    public List<Appointment> findByPatientName(String query){
      return   appointmentRepository.findByPatientContainingIgnoreCase(query);
    }

    //find by doctor
    public List<Appointment> findByDoctor(String query){
        return appointmentRepository.findByDoctorContainingIgnoreCase(query);
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
