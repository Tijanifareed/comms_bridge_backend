package com.capstone.africa.semicolon.comms_bridge.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String userEmail;
    private String phoneNumber;
    private String address;
    private String profilePicture;
    private String preferredLanguage;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
