package com.capstone.africa.semicolon.comms_bridge.controllers;


import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ForgetPasswordRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.UpdatePasswordRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.*;
import com.capstone.africa.semicolon.comms_bridge.services.UserService.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class UserController {

    @Autowired
    private AppUserService userService;

    @PostMapping("create/new/account")
    public ResponseEntity<?> createNewAccount(@RequestBody RegisterUserRequest request){
        try{
            RegisterUserResponse response = userService.register(request);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),BAD_REQUEST);
        }
    }

    @PostMapping("login/existing/account")
    public ResponseEntity<?> loginAccount(@RequestBody LoginRequest request){
        try{
            System.out.println(request.toString());
            LoginResponse response = userService.verifyUserWith(request);
            System.out.println(response.getToken());
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),BAD_REQUEST);
        }
    }
    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request){
        try{
            UpdatePasswordResponse response = userService.updatePassword(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ForgetPasswordRequest request){
        try{
            ForgetPasswordResponse response = userService.resetPassword(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }

}
