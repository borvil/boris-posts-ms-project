package com.example.postservice;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class PostServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(PostServiceApplication.class, args);
	}


}
