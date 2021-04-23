package com.example.userservice.transfers;

import com.example.userservice.entities.User;
import com.example.userservice.responses.UserResponseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserServiceDTO implements Serializable {

    private static final long serialVersionUID = 915471967546776210L;
    private long id;
    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    //private Collection<String> roles;

    public UserResponseDTO toResponseDTO(){
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(this.userId.toString());
        responseDTO.setFirstName(this.firstName);
        responseDTO.setLastName(this.lastName);
        responseDTO.setUsername(this.username);
        responseDTO.setEmail(this.email);
        return responseDTO;
    }

    public User toEntity() {
        User entity = new User();
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setUsername(this.username);
        entity.setEmail(this.email);
        entity.setPassword(this.password);
        return entity;
    }


}
