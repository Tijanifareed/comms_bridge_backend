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
import com.capstone.africa.semicolon.comms_bridge.exception.CommsBridgeException;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import com.capstone.africa.semicolon.comms_bridge.services.jwt_services.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    JavaMailSender mailSender;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


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
        AppUser savedUser = userRepository.save(user);
        sendEmailForUserRegistration(request.getUserEmail(),request.getUserName());
        RegisterUserResponse response = new RegisterUserResponse();
        response.setUserId(savedUser.getId());
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
        String code = generateCode();
        sendEmailForOtpCode(request.getEmail(), code);
        ForgetPasswordResponse response = new ForgetPasswordResponse();
        response.setMessage("Enter the six-digit code that has been sent to you");
        response.setCode(code);
        return response;
    }

    @Override
    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
        AppUser appUser = findById(request.getId());
        String encodedPassword = encoder.encode(request.getNewPassword());
        appUser.setPassword(encodedPassword);
        userRepository.save(appUser);
        UpdatePasswordResponse response = new UpdatePasswordResponse();
        response.setMessage("Password updated successfully");
        return response;
    }
    private AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new CommsBridgeException("User not found"));
    }

    private String generateCode(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000000));
    }

    @Value("$(CommsBridge)")
    private  String fromEmailId;
    public void sendEmailForUserRegistration(String userEmail, String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(userEmail);
        message.setSubject("CommsBridge Account Verification");
        message.setText(String.format(
                """
                        Hi %s,
                                                
                        Welcome to CommsBridge, where communication becomes more accessible and inclusive! We're thrilled to have you join our community.
                                                
                        With CommsBridge, you can seamlessly connect with your loved ones and the world around you. Whether it's through real-time transcription, sign language resources, or other features designed specifically for hearing-impaired individuals, we’re here to bridge the communication gap.
                                                
                        Our mission is to empower you with tools that make every conversation more meaningful and accessible. If you have any questions or need assistance, our support team is just a message away.
                                                
                        Thank you for choosing CommsBridge—we're excited to be part of your journey!
                                                
                        Warm regards, 
                        The CommsBridge Team
                        """,name));
        mailSender.send(message);
    }

   public void sendEmailForOtpCode(String userEmail, String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(userEmail);
        message.setSubject("CommsBridge Password-Reset");
        message.setText(String.format(
                """
                        Below is the Six-Digit code to reset your password
                        %s
                        If You did not request for this reset You can just ignore this 
                        """, code));
        mailSender.send(message);
   }

}
