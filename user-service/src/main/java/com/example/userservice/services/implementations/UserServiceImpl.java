package com.example.userservice.services.implementations;

import com.example.userservice.entities.User;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.interfaces.IUserService;
import com.example.userservice.transfers.UserServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {


    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserServiceDTO getUserByUserId(UUID userId) {
        Optional<User> user= userRepository.findUserByUserId(userId);

        if (!user.isPresent()){
            throw new RuntimeException("The exception is here");
        }
        UserServiceDTO userServiceDTO = user.get().toUserServiceDTO();

        return userServiceDTO;
    }

    @Override
    @Transactional
    public Collection<UserServiceDTO> getUserList() {
        return userRepository
                .findAll()
                .stream()
                .map(u->u.toUserServiceDTO())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserServiceDTO createUser(UserServiceDTO userServiceDTO) {
        User candidateSavedUser = userServiceDTO.toEntity();
        User user = userRepository.save(candidateSavedUser);
        return user.toUserServiceDTO();
    }
    @Override
    @Transactional
    public UserServiceDTO updateUser(UUID userId, UserServiceDTO userServiceDTO) {

        Optional<User> optionalUser = userRepository.findUserByUserId(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("The user was not found");
        }
        optionalUser.get().setUserId(userServiceDTO.getUserId());
        optionalUser.get().setFirstName(userServiceDTO.getFirstName());
        optionalUser.get().setLastName(userServiceDTO.getLastName());
        optionalUser.get().setUsername(userServiceDTO.getUsername());
        optionalUser.get().setEmail(userServiceDTO.getEmail());
        return optionalUser.get().toUserServiceDTO();

    }

    @Override
    @Transactional
    public void deleteUserByUserId(UUID userId) {
        Optional<User> user= userRepository.findUserByUserId(userId);

        if (!user.isPresent()){
            throw new RuntimeException("The user was not found is here");
        }
        userRepository.deleteUserByUserId(userId);
    }
}
