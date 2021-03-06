package ru.geekbrains.lesson7.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    private String email;
    private List<String> roles;

}
