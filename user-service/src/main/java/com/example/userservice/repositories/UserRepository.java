package com.example.userservice.repositories;

import com.example.userservice.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findUserByUserId(UUID userId);
    User deleteUserByUserId(UUID userId);
}
