package by.urbel.task05.controller;

import by.urbel.task05.model.Message;
import by.urbel.task05.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private static final String USERNAME_ATTRIBUTE_NAME = "username";

    @GetMapping()
    public String findMessages(Model model, HttpSession session) {
        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE_NAME);
        if (username == null) {
            return "login";
        }
        List<Message> messages = messageService.findMessages(username);
        model.addAttribute("messages", messages);
        model.addAttribute("newMessage", new Message());
        model.addAttribute("recipients", messageService.findAllRecipient());
        return "messages";
    }

    @PostMapping()
    public String createMessage(@ModelAttribute Message message, @SessionAttribute String username) {
        message.setSender(username);
        messageService.createMessage(message);
        return "redirect:/messages";
    }
}
