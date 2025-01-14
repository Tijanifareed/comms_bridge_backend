package com.capstone.africa.semicolon.comms_bridge.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePasswordRequest {
    private Long id;
    private String newPassword;
}
