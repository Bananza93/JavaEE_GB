package ru.geekbrains.lesson7.mapper;

import ru.geekbrains.lesson7.dto.UserCheckoutDto;
import ru.geekbrains.lesson7.dto.UserDto;
import ru.geekbrains.lesson7.model.DeliveryAddress;
import ru.geekbrains.lesson7.model.Role;
import ru.geekbrains.lesson7.model.AppUser;
import ru.geekbrains.lesson7.model.UserPersonalData;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto userToUserDto(AppUser user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    public static UserCheckoutDto userToUserCheckoutDto(AppUser user) {
        UserCheckoutDto.UserCheckoutDtoBuilder userCheckoutDto = UserCheckoutDto.builder();

        userCheckoutDto.email(user.getEmail());

        UserPersonalData upd = user.getPersonalData();
        if (upd != null) {
            userCheckoutDto
                    .surname(upd.getSurname())
                    .name(upd.getName())
                    .phoneNumber(upd.getPhoneNumber());
        }

        DeliveryAddress da = user.getDeliveryAddress();
        if (da != null) {
            userCheckoutDto
                    .postcode(da.getPostcode())
                    .city(da.getCity())
                    .street(da.getStreet())
                    .building(da.getBuilding())
                    .entrance(da.getEntrance())
                    .floor(da.getFloor())
                    .apartment(da.getApartment());
        }

        return userCheckoutDto.build();
    }

    public static AppUser userCheckoutDtoToUser(UserCheckoutDto userCheckoutDto, AppUser user) {
        if (user.getPersonalData() == null) {
            user.setPersonalData(new UserPersonalData());
        }

        if (user.getDeliveryAddress() == null) {
            user.setDeliveryAddress(new DeliveryAddress());
        }

        UserPersonalData upd = user.getPersonalData();
        if (user.getId() != null) upd.setUser(user);
        upd.setSurname(userCheckoutDto.getSurname());
        upd.setName(userCheckoutDto.getName());
        upd.setPhoneNumber(userCheckoutDto.getPhoneNumber());

        DeliveryAddress da = user.getDeliveryAddress();
        if (user.getId() != null) da.setUser(user);
        da.setPostcode(userCheckoutDto.getPostcode());
        da.setCity(userCheckoutDto.getCity());
        da.setStreet(userCheckoutDto.getStreet());
        da.setBuilding(userCheckoutDto.getBuilding());
        da.setEntrance(userCheckoutDto.getEntrance());
        da.setFloor(userCheckoutDto.getFloor());
        da.setApartment(userCheckoutDto.getApartment());

        return user;
    }

    public static AppUser userCheckoutDtoToUser(UserCheckoutDto userCheckoutDto) {
        return userCheckoutDtoToUser(userCheckoutDto, new AppUser());
    }
}
