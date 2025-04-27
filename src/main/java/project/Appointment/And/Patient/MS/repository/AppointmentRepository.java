package project.Appointment.And.Patient.MS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.Appointment.And.Patient.MS.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);
    List<Appointment> findByDoctorId(Long id);
    List<Appointment> findByPatient_id(Long id);
    List<Appointment> findByTime(LocalDateTime appointmentTime);
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findByPatientIdAndDateAfter(Long patientId, LocalDate date);
    void deleteByDoctorId(Long doctorId);
    boolean existsByDoctorIdAndPatientIdAndStatus(Long doctorId, Long patientId, Appointment.AppointmentStatus status);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Appointment> findByTimeBetween(LocalTime startOfDay, LocalDateTime endOfDay);
    @Query("SELECT a FROM Appointment a WHERE a.date = :date AND a.time BETWEEN :startTime AND :endTime AND a.status = :status")
    List<Appointment> findByDateAndTimeBetweenAndStatus(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("status") Appointment.AppointmentStatus status);
}

