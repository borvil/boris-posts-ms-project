package com.example.userservice.requests;

import lombok.Data;

@Data
public class UserPostRequestDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;


}
