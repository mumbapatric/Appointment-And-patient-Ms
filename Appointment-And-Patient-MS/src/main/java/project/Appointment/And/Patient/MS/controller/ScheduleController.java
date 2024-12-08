package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Schedule;
import project.Appointment.And.Patient.MS.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    //add schedule
    @PostMapping
    public ResponseEntity<String> addSchedule(@RequestBody Schedule schedule){
        Schedule schedules = scheduleService.addSchedule(schedule);
        return ResponseEntity.status(201).body("Schedule added successful");
    }

    //find all
    @GetMapping
    public ResponseEntity<List<Schedule>> findAll(){
        List<Schedule> schedules = scheduleService.findAll();
        return ResponseEntity.ok(schedules);
    }

    //find By id
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findById(@PathVariable Long id){
        Schedule schedule = scheduleService.findById(id);
        if (schedule != null){
            return ResponseEntity.ok(schedule);
        }
        return ResponseEntity.notFound().build();
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule){
        Schedule schedules = scheduleService.updateSchedule(id, schedule);
        if (schedules != null){
            return ResponseEntity.ok(schedules);
        }
        return ResponseEntity.notFound().build();
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
        boolean isDeleted = scheduleService.deleteSchedule(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
