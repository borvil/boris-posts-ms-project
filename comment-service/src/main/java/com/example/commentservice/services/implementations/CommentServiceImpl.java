package com.example.commentservice.services.implementations;

import com.example.commentservice.entities.Comment;
import com.example.commentservice.repositories.CommentRepository;
import com.example.commentservice.services.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, Comment comment) {

        Optional<Comment> optionalComment = commentRepository.findById(id);
        if(!optionalComment.isPresent()){
            throw new RuntimeException("The comment is not exist");
        }
        optionalComment.get().setComment(comment.getComment());
        optionalComment.get().setUserId(comment.getUserId());
        return optionalComment.get();

    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.getCommentsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUserIdAndPostId(Long userId, Long postId) {
        return commentRepository.getCommentsByUserIdAndPostId(userId, postId);
    }


}
