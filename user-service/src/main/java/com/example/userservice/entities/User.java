package com.example.userservice.entities;

import com.example.userservice.transfers.UserServiceDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "update course set is_deleted=true where id=?")
@Where(clause = "is_deleted = false")
public class User implements Serializable {

    private static final long serialVersionUID = -3609553157826691706L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID userId = UUID.randomUUID();

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String encryptedPassword;

    private String emailVerificationToken;

    private Boolean emailVerificationStatus = false;

    //private Collection<String> roles;

    @JsonIgnore
    @Column(name = "isDeleted")
    private boolean isDeleted;


    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserServiceDTO toUserServiceDTO() {
        UserServiceDTO userServiceDTO = new UserServiceDTO();
        userServiceDTO.setId(this.id);
        userServiceDTO.setUserId(this.userId);
        userServiceDTO.setFirstName(this.firstName);
        userServiceDTO.setLastName(this.lastName);
        userServiceDTO.setUsername(this.username);
        userServiceDTO.setEmail(this.email);
        userServiceDTO.setPassword(this.password);
        userServiceDTO.setEncryptedPassword(this.encryptedPassword);
        userServiceDTO.setEmailVerificationStatus(this.emailVerificationStatus);
        userServiceDTO.setEmailVerificationToken(this.getEmailVerificationToken());
        //userServiceDTO.setRoles(this.roles);
        return userServiceDTO;
    }
}
