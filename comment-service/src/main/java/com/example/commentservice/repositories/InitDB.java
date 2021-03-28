package com.example.commentservice.repositories;

import com.example.commentservice.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Comment comment1 = new Comment(2L, 1L, "This is Comment 1");
        Comment comment2 = new Comment(1L, 1L, "This is Comment 2");
        Comment comment3 = new Comment(2L, 1L, "This is Comment 3");



        List<Comment> commentList = Arrays.asList(comment1, comment2, comment3);
        commentRepository.saveAll(commentList);
    }
}
