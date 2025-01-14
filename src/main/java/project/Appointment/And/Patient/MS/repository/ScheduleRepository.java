package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Appointment.And.Patient.MS.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
