package com.example.commentservice.services.interfaces;

import com.example.commentservice.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    Optional<Comment> getCommentById(Long id);
    List<Comment> getCommentList();
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment);
    void deleteCommentById(Long id);
    //Kafka
    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByUserIdAndPostId(Long userId, Long postId);


}
