package project.Appointment.And.Patient.MS.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.dto.ApiResponse;
import project.Appointment.And.Patient.MS.dto.RegisterDoctorDTO;
import project.Appointment.And.Patient.MS.model.Announcement;
import project.Appointment.And.Patient.MS.model.Appointment;
import project.Appointment.And.Patient.MS.model.Doctor;
import project.Appointment.And.Patient.MS.service.AdminService;
import project.Appointment.And.Patient.MS.service.AnnouncementService;
import project.Appointment.And.Patient.MS.service.DoctorService;
import project.Appointment.And.Patient.MS.util.ApiResponseBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class  AdminController {

    private final AdminService adminService;
    private final DoctorService doctorService;
    private final AnnouncementService announcementService;

    public AdminController(AdminService adminService, DoctorService doctorService, AnnouncementService announcementService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.announcementService = announcementService;
    }

    @GetMapping("/dashboard/total-appointments")
    public ResponseEntity<Long> getTotalAppointments() {
        return ResponseEntity.ok(adminService.getTotalAppointments());
    }

    @GetMapping("/dashboard/total-users")
    public ResponseEntity<Long> getTotalRegisteredUsers() {
        return ResponseEntity.ok(adminService.getTotalRegisteredUsers());
    }

    @GetMapping("/dashboard/activity-logs")
    public ResponseEntity<List<Appointment>> getActivityLogs() {
        return ResponseEntity.ok(adminService.getActivityLogs());
    }

    @GetMapping("/dashboard/total-doctors")
    public ResponseEntity<Long> getTotalDoctors() {
        return ResponseEntity.ok(doctorService.getTotalDoctors());
    }

    @GetMapping("/dashboard/total-patients")
    public ResponseEntity<Long> getTotalPatients() {
        return ResponseEntity.ok(adminService.getTotalPatients());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Doctor>> addDoctor(@RequestBody RegisterDoctorDTO doctor) {
        Doctor doctorCreated = doctorService.addDoctor(doctor);
        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                doctorCreated,
                HttpStatus.CREATED
        ));
    }
    // find doctor by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Doctor>> findById(@PathVariable Long id){
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                doctor,
                HttpStatus.OK
        ));
    }

    // find all doctors
    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<Doctor>>> findAll(){
        List<Doctor> doctors = doctorService.findAll();
        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                doctors,
                HttpStatus.OK
        ));
    }

    // update doctor
    @PostMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor){
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    // delete doctor
    @Transactional
    @DeleteMapping
    public ResponseEntity<Void> deleteDoctor(@RequestParam String email) {
        boolean isDeleted = doctorService.deleteDoctor(email);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @PostMapping("/announcement")
    public ResponseEntity<?> create(@RequestBody Map<String, String> req) {
        Announcement a = announcementService.createAnnouncement(req.get("message"));
        return ResponseEntity.ok(a);
    }

    // Users fetch the latest active announcement
    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        return announcementService.getLatestActive()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
