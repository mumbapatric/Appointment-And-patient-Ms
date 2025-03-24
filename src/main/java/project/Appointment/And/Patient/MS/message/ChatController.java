package project.Appointment.And.Patient.MS.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.Appointment.And.Patient.MS.model.Message;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // Receives messages
    @SendTo("/topic/messages") // Sends messages to connected clients
    public Message sendMessage(Message message) {
        return message;
    }
}

