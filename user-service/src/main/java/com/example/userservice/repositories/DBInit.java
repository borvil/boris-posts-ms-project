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
                new User("John", "Smith", "JohnSmith", "john.smith@gmail.com", "pass11"),
                new User("Peter", "Vandaam", "PeterVAn", "peter.vandam@gmail.com", "pass22"),
                new User("Marie", "Dupont", "MarieDupont", "Marie.dupont@gmail.com", "pass33")
        );

        List<User> userList = (List<User>) userRepository.saveAll(users);
        userList.stream().forEach(System.out::println);


    }
}