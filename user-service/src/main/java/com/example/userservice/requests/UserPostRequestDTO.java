package com.example.userservice.requests;

import com.example.userservice.transfers.UserServiceDTO;
import lombok.Data;

@Data
public class UserPostRequestDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    public UserServiceDTO toUserServiceDTO(){
        UserServiceDTO userServiceDTO = new UserServiceDTO();
        userServiceDTO.setFirstName(this.firstName);
        userServiceDTO.setLastName(this.lastName);
        userServiceDTO.setUsername(this.username);
        userServiceDTO.setEmail(this.email);
        userServiceDTO.setPassword(this.password);
        return userServiceDTO;
    }
}
