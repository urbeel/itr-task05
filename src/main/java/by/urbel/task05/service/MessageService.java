package by.urbel.task05.service;

import by.urbel.task05.model.Message;
import by.urbel.task05.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> findMessages(String recipient) {
        return messageRepository.findAllByRecipientOrderByTimeDesc(recipient);
    }

    public Message createMessage(Message message) {
        if (message.getId() == null) {
            return messageRepository.save(message);
        }
        return message;
    }

    public List<String> findAllRecipient() {
        return messageRepository.findAllRecipients();
    }
}
