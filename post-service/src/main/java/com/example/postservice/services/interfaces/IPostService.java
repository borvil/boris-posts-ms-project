package com.example.postservice.services.interfaces;

import com.example.postservice.entities.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {

    Optional<Post> getPostById(Long id);
    List<Post> getPostList();
    Post createPost(Post post);
    Post updatePost(Long id, Post post);
    void deletePostById(Long id);

}
