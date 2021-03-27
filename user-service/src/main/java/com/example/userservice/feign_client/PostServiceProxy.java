package com.example.userservice.feign_client;

import com.example.userservice.beans.PostBean;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.h2.util.json.JSONObject;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="api-gateway-service")
@RibbonClient(name="post-service")
public interface PostServiceProxy {

    @GetMapping("/post-service/posts/{id}")
    PostBean getPost(@PathVariable("id") Long id);

    @GetMapping("/post-service/posts/userid/{id}")
    List<PostBean> getAllPostByUserId(@PathVariable("id") Long id);

    @PostMapping("/post-service/posts/feign_posts/{userId}")
    PostBean addPostByUser(@PathVariable("userId")Long userId, @RequestBody String post);

    @DeleteMapping("/post-service/posts/feign_post/userid/{userId}/postid/{postId}")
    void deletePost(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId);



}
