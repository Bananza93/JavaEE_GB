package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @GetMapping
    public String getRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public RedirectView registration(@RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam String confirmPassword,
                                     final RedirectAttributes attributes) {
        if (!email.matches("^[\\w\\.]+@[\\w\\.]+$")) {
            attributes.addFlashAttribute("message", "Incorrect email");
            return new RedirectView("/register", true);
        }
        if (!password.equals(confirmPassword)) {
            attributes.addFlashAttribute("message", "Passwords doesn't match");
            return new RedirectView("/register", true);
        }
        attributes.addFlashAttribute("userEmail", email);
        return new RedirectView("/register/confirm", true);
    }

    @GetMapping("/confirm")
    public String getRegistrationConfirmPage() {
        return "registration_confirm";
    }
}
