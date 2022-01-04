package ru.geekbrains.lesson7.mapper;

import ru.geekbrains.lesson7.dto.UserDto;
import ru.geekbrains.lesson7.model.Role;
import ru.geekbrains.lesson7.model.User;

import java.util.stream.Collectors;

public class UserMapper {
    
    public static UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDto;
    }
}
