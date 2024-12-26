package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.LoginResponse;

public interface AppUserService {
    LoginResponse verifyUserWith(LoginRequest request);

}
