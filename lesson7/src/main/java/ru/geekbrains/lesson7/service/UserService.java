package ru.geekbrains.lesson7.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.dto.UserDto;
import ru.geekbrains.lesson7.mapper.UserMapper;
import ru.geekbrains.lesson7.model.AppUser;
import ru.geekbrains.lesson7.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @TrackExecutionTime
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        user.isEnabled(),
                        true,
                        true,
                        true,
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toSet())
                )).orElseThrow(() -> new UsernameNotFoundException("User \"" + username + "\" not found"));
    }

    @TrackExecutionTime
    public List<UserDto> getAllUsersDto() {
        return userRepository.findAll().stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }

    @TrackExecutionTime
    public AppUser getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<AppUser> getActiveManagers() {
        return userRepository.findAllFetchRole().stream()
                .filter(AppUser::isEnabled)
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_MANAGER")))
                .collect(Collectors.toList());
    }
}
