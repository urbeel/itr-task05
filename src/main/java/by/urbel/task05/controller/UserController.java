package by.urbel.task05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USERNAME_ATTRIBUTE_NAME = "username";

    @PostMapping()
    public String login(@RequestParam String username, HttpSession session) {
        username = username.trim();
        if (username.isEmpty()) {
            return "login";
        }
        session.setAttribute(USERNAME_ATTRIBUTE_NAME, username);
        return "redirect:/messages";
    }
}
