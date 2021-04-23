package com.example.userservice.services.implementations;

import com.example.userservice.entities.User;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.interfaces.IUserService;
import com.example.userservice.transfers.UserServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {


    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public UserServiceDTO getUserById(Long id) {
        Optional<User> user= userRepository.findById(id);

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
    public User updateUser(Long id, User user) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("The user is not exist");
        }
        optionalUser.get().setLastName(user.getLastName());
        optionalUser.get().setFirstName(user.getFirstName());
        return optionalUser.get();

    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
