package com.example.userservice.controllers;

import com.example.userservice.beans.PostBean;
import com.example.userservice.entities.User;
import com.example.userservice.feign_client.PostServiceProxy;
import com.example.userservice.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/feign-proxy-post/{id}")
    public PostBean getPost(@PathVariable Long id) {
        PostBean response = feignPostServiceProxy.getPost(id);
        log.info("{}", response);
        return new PostBean(response.getId(), response.getPost(), response.getUserId());

    }

    @GetMapping("/feign-proxy-posts/{id}")
    public List<PostBean> getPostsByUserId(@PathVariable Long id) {
        List<PostBean> response = feignPostServiceProxy.getAllPostByUserId(id);
        log.info("{}", response);
        List<PostBean> resultList = new ArrayList<>();
        for (PostBean p : response) {
            resultList.add(new PostBean(p.getId(), p.getPost(), p.getUserId()));
        }
        return resultList;

    }


}