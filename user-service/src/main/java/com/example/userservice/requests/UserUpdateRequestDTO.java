package com.example.userservice.requests;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {

    private String businessKey;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}
