package project.Appointment.And.Patient.MS.service;

import org.springframework.stereotype.Service;
import project.Appointment.And.Patient.MS.model.User;
import project.Appointment.And.Patient.MS.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // add user
    public User addUser(User user){
        return  userRepository.save(user);
    }

    // find all
    public List<User> findAll(){
        return userRepository.findAll();
    }

    //findById
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user not found" + id));
    }

    //update user
    public User updateUser(Long id , User updatedUser){
        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user not found" + id));
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    // delete user
    public boolean deleteUser(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
