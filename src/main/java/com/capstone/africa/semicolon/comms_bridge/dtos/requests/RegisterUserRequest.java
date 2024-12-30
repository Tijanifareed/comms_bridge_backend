package com.capstone.africa.semicolon.comms_bridge.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
    private String userEmail;
    private String phoneNumber;
    private String userName;
    private String password;

}
