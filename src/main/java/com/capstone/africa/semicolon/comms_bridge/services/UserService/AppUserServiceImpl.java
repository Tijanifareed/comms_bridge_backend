package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.LoginResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import com.capstone.africa.semicolon.comms_bridge.services.jwt_services.JWTService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtService;

    private UserRepository userRepository;


    @Override
    public LoginResponse verifyUserWith(LoginRequest request) {
        String userName = request.getUsername();
        AppUser appUser = userRepository.findByUserName(userName);
        Authentication authentication =
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            LoginResponse response = new LoginResponse();
            response.setMessage("Success");
            String token = jwtService.generateToken(request.getUsername(), appUser.getId());
            response.setToken(token);
            response.setUserName(userName);
            response.setRole(appUser.getRole().toString());
            return response;
        }
        LoginResponse response = new LoginResponse();
        response.setMessage("failed");
        return response;
    }
}
