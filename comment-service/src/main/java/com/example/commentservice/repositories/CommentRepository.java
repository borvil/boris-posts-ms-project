package com.example.commentservice.repositories;

import com.example.commentservice.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByUserIdAndPostId(Long userId, Long postId);


}
