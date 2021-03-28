package com.example.commentservice.controllers;

import com.example.commentservice.entities.Comment;
import com.example.commentservice.services.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final ICommentService commentService;


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
