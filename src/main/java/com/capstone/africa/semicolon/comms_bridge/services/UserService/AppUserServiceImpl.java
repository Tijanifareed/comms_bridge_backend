package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.LoginResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.RegisterUserResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;
import com.capstone.africa.semicolon.comms_bridge.exception.CommsBridgeException;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import com.capstone.africa.semicolon.comms_bridge.services.jwt_services.JWTService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


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
        String encodedPassword = encoder.encode(request.getPassword());
        AppUser user = new AppUser();
        user.setUserEmail(request.getUserEmail());
        user.setPassword(request.getPassword());
        user.setUserName(request.getUserName());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("Successfully Registered");
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
