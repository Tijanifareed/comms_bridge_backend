package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ForgetPasswordRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.LoginRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ForgetPasswordResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.LoginResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.RegisterUserResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;
import com.capstone.africa.semicolon.comms_bridge.exception.CommsBridgeException;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import com.capstone.africa.semicolon.comms_bridge.services.jwt_services.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public LoginResponse verifyUserWith(LoginRequest request) {
        String userEmail = request.getEmail();
        AppUser user = userRepository.findByUserEmail(userEmail);
        if (user==null) throw new UsernameNotFoundException("User Not Found");
        String userName = user.getUserName();
        AppUser appUser = userRepository.findByUserName(userName);
        Authentication authentication =
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), request.getPassword()));
        if(authentication.isAuthenticated()){
            LoginResponse response = new LoginResponse();
            response.setMessage("Success");
            String token = jwtService.generateToken(user.getUserName(), appUser.getId());
            response.setToken(token);
            response.setUserName(userName);
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
        user.setPassword(encodedPassword);
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

    @Override
    public ForgetPasswordResponse resetPassword(ForgetPasswordRequest request) {
        AppUser appUser = userRepository.findByUserEmail(request.getEmail());
        if(appUser==null) throw new CommsBridgeException("User Does not exist");
        ForgetPasswordResponse response = new ForgetPasswordResponse();
        response.setMessage("Enter the code that has been sent to you");
        response.setCode(generateCode());
        return response;
    }

    private String generateCode(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000000));
    }
}
