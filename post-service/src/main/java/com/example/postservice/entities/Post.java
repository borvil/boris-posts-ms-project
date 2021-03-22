package com.example.postservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Post {

    public Post(String post, Long userId) {
        this.post = post;
        this.userId = userId;
    }

    public Post(String post) {
        this.post = post;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String post;

    @Column(nullable = false)
    private Long userId;

}
