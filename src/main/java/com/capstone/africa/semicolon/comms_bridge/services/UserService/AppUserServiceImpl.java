package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.LoginResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.RegisterUserResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;
import com.capstone.africa.semicolon.comms_bridge.exception.CommsBridgeException;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import com.capstone.africa.semicolon.comms_bridge.services.jwt_services.JWTService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtService;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;


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

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        validateUserEmail(request.getUserEmail());
        passwordEncoder.encode(request.getPassword());
        AppUser user = modelMapper.map(request, AppUser.class);
        userRepository.save(user);
        RegisterUserResponse response = modelMapper.map(request, RegisterUserResponse.class);
        response.setResponse("Successfully Registered");
        response.setAppUserId(response.getAppUserId());
        response.setEmail(response.getEmail());
        return response;
    }
    private void validateUserEmail(String email){
        boolean existsByEmail = userRepository.existsByUserEmail(email);
        if (existsByEmail) throw new CommsBridgeException("User already exist");
    }


    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
}
