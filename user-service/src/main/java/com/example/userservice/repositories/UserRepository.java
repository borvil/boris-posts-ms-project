package com.example.userservice.repositories;

import com.example.userservice.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findUserByUserId(UUID userId);
    void deleteUserByUserId(UUID userId);
}
