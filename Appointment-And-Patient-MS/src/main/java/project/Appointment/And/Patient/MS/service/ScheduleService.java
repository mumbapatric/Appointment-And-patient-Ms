package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.Schedule;
import project.Appointment.And.Patient.MS.repository.ScheduleRepository;

import java.util.List;


@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // add schedule
    public Schedule addSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    //find All
    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    // find by id
    public Schedule findById(Long id){
        return  scheduleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("schedule not found"));
    }

    //update
    public Schedule updateSchedule(Long id, Schedule updatedSchedule){
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElse(null);
       if (existingSchedule !=null){
           existingSchedule.setDayOfWeek(updatedSchedule.getDayOfWeek());
           existingSchedule.setDoctorId(updatedSchedule.getDoctorId());
           existingSchedule.setStartDate(updatedSchedule.getStartDate());
           existingSchedule.setEndDateTime(updatedSchedule.getEndDateTime());
           existingSchedule.setHospitalId(updatedSchedule.getHospitalId());
           return scheduleRepository.save(existingSchedule);
       }
       return null;
    }

    //delete
    public boolean deleteSchedule(Long id){
        if (scheduleRepository.existsById(id)){
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
