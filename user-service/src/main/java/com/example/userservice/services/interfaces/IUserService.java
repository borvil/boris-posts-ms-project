package com.example.userservice.services.interfaces;

import com.example.userservice.entities.User;
import com.example.userservice.transfers.UserServiceDTO;

import java.util.Collection;

public interface IUserService {
    UserServiceDTO getUserById(Long id);
    Collection<UserServiceDTO> getUserList();
    UserServiceDTO createUser(UserServiceDTO userServiceDTO);
    User updateUser(Long id, User user);
    void deleteUserById(Long id);

}
