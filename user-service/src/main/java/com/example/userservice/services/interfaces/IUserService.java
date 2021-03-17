package com.example.userservice.services.interfaces;

import com.example.userservice.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getUserById(Long id);
    List<User> getUserList();
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUserById(Long id);

}
