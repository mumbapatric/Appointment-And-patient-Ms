package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //add user
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user){

        userService.addUser(user);
        return ResponseEntity.status(201).body("user added successful");
    }

    // find all
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        if (user !=null){
            return  ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    //update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User users = userService.updateUser(id, user);
        if (users != null){
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
