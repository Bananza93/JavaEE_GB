package ru.geekbrains.lesson7.dto;

import lombok.Data;

@Data
public class UserCheckoutDto {

    private String surname;
    private String name;
    private String email;
    private String phoneNumber;
    private String postcode;
    private String city;
    private String street;
    private String building;
    private String entrance;
    private String floor;
    private String apartment;
}
