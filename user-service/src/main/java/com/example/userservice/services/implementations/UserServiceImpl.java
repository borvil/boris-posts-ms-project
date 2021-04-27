package com.example.userservice.services.implementations;

import com.example.userservice.entities.User;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.interfaces.IUserService;
import com.example.userservice.transfers.UserServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Optional<User> user = userRepository.findUserByUserId(userId);

        if (!user.isPresent()) {
            throw new RuntimeException("The exception is here");
        }
        UserServiceDTO userServiceDTO = user.get().toUserServiceDTO();

        return userServiceDTO;
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<UserServiceDTO> getUserList(int page, int limit, String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<User> users = userRepository.findAll(pageable);
        List<User> userList = users.getContent();
        List<UserServiceDTO> userServiceDTOList = userList.stream().map(user -> user.toUserServiceDTO()).collect(Collectors.toList());
        return userServiceDTOList;
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
        Optional<User> user = userRepository.findUserByUserId(userId);

        if (!user.isPresent()) {
            throw new RuntimeException("The user was not found is here");
        }
        userRepository.deleteUserByUserId(userId);
    }
}
