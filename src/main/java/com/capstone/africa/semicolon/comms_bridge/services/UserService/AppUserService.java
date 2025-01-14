package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ForgetPasswordRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.UpdatePasswordRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ForgetPasswordResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.LoginResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.RegisterUserResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.UpdatePasswordResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;

import java.util.Collection;
import java.util.List;

public interface AppUserService {

    LoginResponse verifyUserWith(LoginRequest request);

    RegisterUserResponse register(RegisterUserRequest request);

    List<AppUser> getAllUsers();

    ForgetPasswordResponse resetPassword(ForgetPasswordRequest request);

    UpdatePasswordResponse updatePassword(UpdatePasswordRequest request);

}
