package com.example.userservice.controllers;

import com.example.userservice.beans.PostBean;
import com.example.userservice.entities.User;
import com.example.userservice.feign_client.PostServiceProxy;
import com.example.userservice.services.interfaces.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final IUserService userService;
    private final PostServiceProxy feignPostServiceProxy;


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id).get();
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUserList();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public User getUser(@PathVariable Long id, @RequestBody User user) {
        return userService.getUserById(id).get();
    }

    //Feign Client  Requests

    @GetMapping("/feign-proxy-post/{id}")
    @HystrixCommand(fallbackMethod = "fallbackGetPost")
    public PostBean getPost(@PathVariable Long id) {
        PostBean response = feignPostServiceProxy.getPost(id);
        log.info("{}", response);
        return new PostBean(response.getId(), response.getPost(), response.getUserId());

    }


    @GetMapping("/feign-proxy-posts/{id}")
    @HystrixCommand(fallbackMethod = "fallbackGetPostsByUserId")
    public List<PostBean> getPostsByUserId(@PathVariable Long id) {
        List<PostBean> response = feignPostServiceProxy.getAllPostByUserId(id);
        log.info("{}", response);
        List<PostBean> resultList = new ArrayList<>();
        for (PostBean p : response) {
            resultList.add(new PostBean(p.getId(), p.getPost(), p.getUserId()));
        }
        return resultList;

    }

    @PostMapping("/feign-proxy-posts/{userId}")
    public PostBean addPostByUser(@PathVariable Long userId,@RequestBody String post) {
        PostBean createdPost = feignPostServiceProxy.addPostByUser(userId, post);
        log.info("{}", createdPost);
        return new PostBean(createdPost.getId(), createdPost.getPost(), createdPost.getUserId());

    }

    //Fault Tolerance

    public PostBean fallbackGetPost(@PathVariable Long id) {
        return new PostBean(10001L, "This is the fallback post message", 2L);
    }

    public List<PostBean> fallbackGetPostsByUserId(@PathVariable Long id) {
        List<PostBean> fallbackPostBeanList = Arrays.asList(new PostBean(10001L, "This is the fallback list post message", 2L),
                new PostBean(10002L, "This is the fallback post list  message", 1L));
     return fallbackPostBeanList;
    }


}