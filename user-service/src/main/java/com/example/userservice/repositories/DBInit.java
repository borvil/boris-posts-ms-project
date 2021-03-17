package com.example.userservice.repositories;

import com.example.userservice.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<User> users = Arrays.asList(
                new User("John", "john.smith@gmail.com", "John", "Smith"),
                new User("Peter", "peter.vandam@gmail.com", "Peter", "Vandam"),
                new User("Marie", "Marie.dupont@gmail.com", "Marie", "Dupont")
        );

        userRepository.saveAll(users);

    }
}