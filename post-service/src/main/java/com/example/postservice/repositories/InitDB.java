package com.example.postservice.repositories;

import com.example.postservice.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {


    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<Post> posts = Arrays.asList(
                new Post("John Post", 1L),
                new Post("Peter Post", 2L),
                new Post("Marie Post", 3L),
                new Post("Peter Second Post ", 2L)
        );
        postRepository.saveAll(posts);
    }
}
