package ru.geekbrains.lesson7.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.lesson7.dto.ConfirmRegistrationDto;
import ru.geekbrains.lesson7.model.RegistrationToken;
import ru.geekbrains.lesson7.model.User;
import ru.geekbrains.lesson7.repository.RegistrationTokenRepository;
import ru.geekbrains.lesson7.repository.RoleRepository;
import ru.geekbrains.lesson7.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void signUp(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            if (!user.isEnabled()) {
                registrationTokenRepository.annulCurrentUserRegistrationToken(user.getId());
            } else {
                throw new IllegalStateException("User " + email + " already exists");
            }
        } else {
            user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(List.of(roleRepository.findRoleByName("ROLE_USER")));
            userRepository.save(user);
        }
        sendConfirmationEmail(createRegistrationToken(user));
    }

    public void resendRegistrationToken(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail).orElse(null);
        sendConfirmationEmail(createRegistrationToken(user));
    }

    private RegistrationToken createRegistrationToken(User user) {
        String tokenUid = UUID.randomUUID().toString();
        var token = new RegistrationToken(user, tokenUid, Instant.now().plus(15, ChronoUnit.MINUTES));
        registrationTokenRepository.save(token);
        return token;
    }

    private void sendConfirmationEmail(RegistrationToken token) {
        emailService.sendVerificationEmail(token.getUser().getEmail(), token.getToken());
    }

    @Transactional
    public ConfirmRegistrationDto confirmRegistration(String tokenUid) {
        ConfirmRegistrationDto dto = new ConfirmRegistrationDto();
        dto.setIsSuccess(false);
        var regToken = registrationTokenRepository.findRegistrationTokenByToken(tokenUid);

        if (regToken.isEmpty()) {
            dto.setReason("tokenNotFound");
        } else {
            var token = regToken.get();
            if (token.getUser().isEnabled()) {
                dto.setReason("userAlreadyEnable");
            } else if (Instant.now().isAfter(token.getExpiredAt())) {
                dto.setReason("tokenExpired");
                dto.setUserEmail(regToken.get().getUser().getEmail());
            } else {
                token.getUser().setEnabled(true);
                dto.setIsSuccess(true);
            }
        }
        return dto;
    }
}
