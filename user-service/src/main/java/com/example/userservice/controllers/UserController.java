package com.example.userservice.controllers;

import com.example.userservice.beans.PostBean;
import com.example.userservice.feign_client.PostServiceProxy;
import com.example.userservice.requests.UserPostRequestDTO;
import com.example.userservice.requests.UserUpdateRequestDTO;
import com.example.userservice.responses.UserResponseDTO;
import com.example.userservice.services.interfaces.IUserService;
import com.example.userservice.transfers.UserServiceDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final IUserService userService;
    private final PostServiceProxy feignPostServiceProxy;


    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String userId) {
        UserServiceDTO userServiceDTO = userService.getUserByUserId(UUID.fromString(userId));
        UserResponseDTO userResponseDTO = userServiceDTO.toResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Collection<UserResponseDTO>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "limit", defaultValue = "50") int limit,
                                                @RequestParam(value = "sort", defaultValue = "email", required = false)
                                                        String sort) {

        List<UserResponseDTO> userResponseDTOList =
                userService.getUserList(page, limit, sort)
                        .stream()
                        .map(u -> u.toResponseDTO())
                        .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTOList);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserPostRequestDTO userPostRequestDTO) {
        UserServiceDTO savedUser = userService.createUser(userPostRequestDTO.toUserServiceDTO());
        UserResponseDTO userResponseDTO = savedUser.toResponseDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@PathVariable String userId) {
        userService.deleteUserByUserId(UUID.fromString(userId));

    }

    @PutMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String userId,
                                      @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        UserServiceDTO userServiceDTO = userService
                .updateUser(UUID.fromString(userId), userUpdateRequestDTO.toUserServiceDTO());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userServiceDTO.toResponseDTO());
    }

    //Feign Client  Requests

    @GetMapping(value = "/feign-proxy-post/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @HystrixCommand(fallbackMethod = "fallbackGetPost")
    public PostBean getPost(@PathVariable Long id) {
        PostBean response = feignPostServiceProxy.getPost(id);
        log.info("{}", response);
        return new PostBean(response.getId(), response.getPost(), response.getUserId());

    }


    @GetMapping(value = "/feign-proxy-posts/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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

    @PostMapping(value = "/feign-proxy-posts/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PostBean addPostByUser(@PathVariable Long userId, @RequestBody String post) {
        PostBean createdPost = feignPostServiceProxy.addPostByUser(userId, post);
        log.info("{}", createdPost);
        return new PostBean(createdPost.getId(), createdPost.getPost(), createdPost.getUserId());

    }

    @DeleteMapping(value = "/feign-proxy-post/userid/{userId}/postid/{postId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void removePost(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        feignPostServiceProxy.deletePost(userId, postId);
    }

    //Fault Tolerance

    public PostBean fallbackGetPost(@PathVariable Long id) {
        return new PostBean(10001L, "This is the fallback post message", 2L);
    }

    public List<PostBean> fallbackGetPostsByUserId(@PathVariable Long id) {
        List<PostBean> fallbackPostBeanList = Arrays.asList(
                new PostBean(10001L, "This is the fallback list post message", 2L),
                new PostBean(10002L, "This is the fallback post list  message", 1L)
        );
        return fallbackPostBeanList;
    }


}