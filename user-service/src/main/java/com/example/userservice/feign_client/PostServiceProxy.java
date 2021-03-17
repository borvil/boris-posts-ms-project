package com.example.userservice.feign_client;

import com.example.userservice.beans.PostBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="api-gateway-service")
@RibbonClient(name="post-service")
public interface PostServiceProxy {

    @GetMapping("/{id}")
    PostBean getPost(@PathVariable("id") Long id);
}
