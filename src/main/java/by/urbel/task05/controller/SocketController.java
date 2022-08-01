package by.urbel.task05.controller;

import by.urbel.task05.model.Message;
import by.urbel.task05.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/sendMessage")
    public Message greeting(@Payload Message message) {
        Message savedMessage = messageService.createMessage(message);
        messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", savedMessage);
        return message;
    }
}
