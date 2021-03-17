package com.example.postservice.controllers;

import com.example.postservice.entities.Post;
import com.example.postservice.services.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {


    private final IPostService postService;

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPostById(id).get();
    }

    @GetMapping
    public List<Post> getAllPost() {
        return postService.getPostList();
    }

    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
    }

    @GetMapping("/userid/{id}")
    public List<Post> getAllPostByUserId(@PathVariable Long id) {
        return postService.getPostsByUserId(id);
    }

}
