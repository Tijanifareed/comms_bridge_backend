package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ForgetPasswordRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ForgetPasswordResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.RegisterUserResponse;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppUserServiceTest {



    @Autowired
    private UserRepository userRepository;




    @Autowired
    private AppUserService appUserService;

    @Autowired
    private UserRepository appUserRepository;

    @BeforeEach
    public void setUp(){
        appUserRepository.deleteAll();
    }
    @Test
    public void testThatAppUserCanRegister(){
        RegisterUserResponse response = registerUserRequest();
        assertThat(response).isNotNull();
        assertThat(appUserService.getAllUsers().size()).isEqualTo(1L);
        assertThat(response.getMessage()).contains("Successfully Registered");
    }

    private RegisterUserResponse registerUserRequest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUserEmail("test@email.coms");
        request.setPassword("password123");
        request.setUserName("testedUsername");
        request.setPhoneNumber("08133608698");
        return appUserService.register(request);
    }

    @Test
    public void testThatUserCanRetrieveTheirPasswordWhenTheyForgetPassword(){
        registerUserRequest();
        ForgetPasswordRequest request = new ForgetPasswordRequest();
        request.setEmail("test@email.com");
        ForgetPasswordResponse response = appUserService.resetPassword(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("Enter the six-digit code that has been sent to you");
        System.out.println(response.getCode());
        assertThat(response.getCode().length()).isEqualTo(6);
    }

}