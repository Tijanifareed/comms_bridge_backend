package com.capstone.africa.semicolon.comms_bridge.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ForgetPasswordResponse {
    private String message;
    private String code;
}
