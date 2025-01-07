package com.capstone.africa.semicolon.comms_bridge.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginRequest {
    private String email;
    private String password;
}
