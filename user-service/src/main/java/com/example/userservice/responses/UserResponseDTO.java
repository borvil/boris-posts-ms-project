package com.example.userservice.responses;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
