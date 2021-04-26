package com.example.userservice.services.interfaces;

import com.example.userservice.entities.User;
import com.example.userservice.transfers.UserServiceDTO;

import java.util.Collection;
import java.util.UUID;

public interface IUserService {
    UserServiceDTO getUserByUserId(UUID userId);
    Collection<UserServiceDTO> getUserList();
    UserServiceDTO createUser(UserServiceDTO userServiceDTO);
    UserServiceDTO updateUser(UUID userId, UserServiceDTO userServiceDTO);
    void deleteUserByUserId(UUID userId);

}
