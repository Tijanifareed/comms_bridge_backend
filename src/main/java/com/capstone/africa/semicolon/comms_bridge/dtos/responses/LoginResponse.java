package com.capstone.africa.semicolon.comms_bridge.dtos.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String Message;
    private String Token;
    private String role;
    private String userName;
}
