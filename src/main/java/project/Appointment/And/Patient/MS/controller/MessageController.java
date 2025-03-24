package project.Appointment.And.Patient.MS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Appointment.And.Patient.MS.model.Message;
import project.Appointment.And.Patient.MS.service.MessageService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam String senderUsername,
                                               @RequestParam String receiverUsername,
                                               @RequestBody String content) {
        Message message = messageService.sendMessage(senderUsername, receiverUsername, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Message>> getUserMessages(@PathVariable String username) {
        return ResponseEntity.ok(messageService.getUserMessages(username));
    }
}
