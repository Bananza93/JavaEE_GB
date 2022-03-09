package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.lesson7.service.RegistrationService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String getRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public RedirectView registration(@RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam String confirmPassword,
                                     final RedirectAttributes attributes) {
        if (!email.matches("^[\\w.]+@[\\w.]+$")) {
            attributes.addFlashAttribute("message", "Incorrect email");
            return new RedirectView("/register", true);
        }
        if (!password.equals(confirmPassword)) {
            attributes.addFlashAttribute("message", "Passwords doesn't match");
            return new RedirectView("/register", true);
        }
        try {
            registrationService.signUp(email, password);
        } catch (IllegalStateException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            return new RedirectView("/register", true);
        }
        attributes.addFlashAttribute("userEmail", email);
        return new RedirectView("/register/confirm", true);
    }

    @GetMapping("/confirm")
    public String getRegistrationConfirmPage() {
        return "registration_confirm";
    }

    @GetMapping("/verify")
    public String getRegistrationCompletePage(@RequestParam String token, Model model) {
        model.addAttribute("verify_result", registrationService.confirmRegistration(token));
        return "registration_verify";
    }

    @GetMapping("/resendToken")
    public RedirectView resendToken(@RequestParam String userEmail, final RedirectAttributes attributes) {
        registrationService.resendRegistrationToken(userEmail);
        attributes.addFlashAttribute("userEmail", userEmail);
        return new RedirectView("/register/confirm", true);
    }
}
