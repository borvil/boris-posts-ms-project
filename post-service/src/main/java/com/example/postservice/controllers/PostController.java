package com.example.postservice.controllers;

import com.example.postservice.entities.Post;
import com.example.postservice.services.interfaces.IPostService;
import com.fasterxml.jackson.databind.util.JSONPObject;
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

    //FeignClient

    @GetMapping("/userid/{id}")
    public List<Post> getAllPostByUserId(@PathVariable Long id) {
        return postService.getPostsByUserId(id);
    }

    @PostMapping("/feign_posts/{userId}")
    public Post addPostByUser(@PathVariable Long userId, @RequestBody String post) {
        Post candidatePost = new Post(post, userId);
        return postService.createPost(candidatePost);
    }
    @DeleteMapping("/feign_post/userid/{userId}/postid/{postId}")
    public void removePostByUser(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId ) {
        List<Post> posts = postService.getPostsByUserId(userId);
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(postId)){
                postService.deletePostById(postId);
            }
        }
    }

}
