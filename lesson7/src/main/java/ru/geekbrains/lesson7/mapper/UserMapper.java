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
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDto;
    }

    public static UserCheckoutDto userToUserCheckoutDto(AppUser user) {
        UserCheckoutDto userCheckoutDto = new UserCheckoutDto();

        userCheckoutDto.setEmail(user.getEmail());
        UserPersonalData upd = user.getPersonalData();
        if (upd != null) {
            userCheckoutDto.setSurname(upd.getSurname());
            userCheckoutDto.setName(upd.getName());
            userCheckoutDto.setPhoneNumber(upd.getPhoneNumber());
        }

        DeliveryAddress da = user.getDeliveryAddress();
        if (da != null) {
            userCheckoutDto.setPostcode(da.getPostcode());
            userCheckoutDto.setCity(da.getCity());
            userCheckoutDto.setStreet(da.getStreet());
            userCheckoutDto.setBuilding(da.getBuilding());
            userCheckoutDto.setEntrance(da.getEntrance());
            userCheckoutDto.setFloor(da.getFloor());
            userCheckoutDto.setApartment(da.getApartment());
        }

        return userCheckoutDto;
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
