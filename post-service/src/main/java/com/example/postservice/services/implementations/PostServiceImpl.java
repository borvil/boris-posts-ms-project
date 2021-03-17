package com.example.postservice.services.implementations;

import com.example.postservice.entities.Post;
import com.example.postservice.repositories.PostRepository;
import com.example.postservice.services.interfaces.IPostService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(Long id, Post post) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){
            throw new RuntimeException("The post is not exist");
        }
        optionalPost.get().setPost(post.getPost());
        optionalPost.get().setUserId(post.getUserId());
        return optionalPost.get();
    }

    @Override
    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByUserId(Long id) {
        return postRepository.getAllByUserId(id);
    }
}
