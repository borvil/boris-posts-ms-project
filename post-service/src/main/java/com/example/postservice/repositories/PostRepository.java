package com.example.postservice.repositories;

import com.example.postservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getAllByUserId(Long id);


}
