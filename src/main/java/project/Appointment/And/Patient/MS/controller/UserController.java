package project.Appointment.And.Patient.MS.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.dto.ApiResponse;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.service.DoctorService;
import project.Appointment.And.Patient.MS.service.PatientService;
import project.Appointment.And.Patient.MS.service.UserService;
import project.Appointment.And.Patient.MS.util.ApiResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Constructor Injection more powerfl than @Autowired
    public UserController(UserService userService, DoctorService doctorService, PatientService patientService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    //add user
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    // find all
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                users,
                HttpStatus.OK
        ));
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse(
                user,
                HttpStatus.OK
        ));
    }

    //update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
