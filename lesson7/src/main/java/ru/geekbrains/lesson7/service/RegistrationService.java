package ru.geekbrains.lesson7.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.repository.RegistrationTokenRepository;
import ru.geekbrains.lesson7.repository.RoleRepository;
import ru.geekbrains.lesson7.repository.UserRepository;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               EmailService emailService,
                               RegistrationTokenRepository registrationTokenRepository,
                               BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.registrationTokenRepository = registrationTokenRepository;
    }
}
