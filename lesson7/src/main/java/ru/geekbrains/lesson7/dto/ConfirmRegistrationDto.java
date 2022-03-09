package ru.geekbrains.lesson7.dto;

import lombok.Data;

@Data
public class ConfirmRegistrationDto {

    private Boolean isSuccess;
    private String userEmail;
    private String reason;
}
