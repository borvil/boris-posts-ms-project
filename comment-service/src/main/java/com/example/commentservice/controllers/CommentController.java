package com.example.commentservice.controllers;

import com.example.commentservice.entities.Comment;
import com.example.commentservice.services.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final ICommentService commentService;

    @Autowired
    private KafkaTemplate<String, List<Comment>> kafkaTemplate;

    private static final String TOPIC = "comment_topic";




    @GetMapping("/kafka/publish/userId/{userId}")
    public String getAllCommentsByUserId(@PathVariable("userId")  Long userId) {

        List<Comment> commentsByUserId = commentService.getCommentsByUserId(userId);

        kafkaTemplate.send(TOPIC, commentsByUserId);

        return "All the Comments of User was send";
    }

    @GetMapping("/kafka/publish/userId/{userId}/postId/{postId}")
    public String getAllCommentsByUserIdAndPostId(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {

        List<Comment> commentsByUserIdAndPostId = commentService.getCommentsByUserIdAndPostId(userId,postId);

        kafkaTemplate.send(TOPIC, commentsByUserIdAndPostId);

        return "All the Comments for the concrete  User and Post  was send";
    }


    @GetMapping
    public List<Comment> getListOfComments(){
        return commentService.getCommentList();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable("id") Long id){
        return commentService.getCommentById(id).get();
    }

    @DeleteMapping("/{id}")
    public void removeComment(@PathVariable("id") Long id){
         commentService.deleteCommentById(id);
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment){
        return commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody Comment comment){
        return commentService.updateComment(id, comment);
    }


}
