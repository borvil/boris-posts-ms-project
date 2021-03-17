package com.example.userservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostBean {

    private Long id;

    private String post;

    private Long userId;

}
