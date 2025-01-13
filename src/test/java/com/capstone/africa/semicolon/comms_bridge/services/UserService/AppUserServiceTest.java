package com.capstone.africa.semicolon.comms_bridge.services.UserService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.RegisterUserRequest;
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

    @BeforeEach
    private void setUp(){
        userRepository.deleteAll();
    }

    @Autowired
    private AppUserService appUserService;
    @Test
    public void testThatAppUserCanRegister(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUserEmail("test@email.com");
        request.setPassword("password123");
        request.setUserName("testedUsername");
        request.setPhoneNumber("08133608698");
        RegisterUserResponse response = appUserService.register(request);
        assertThat(response).isNotNull();
        assertThat(appUserService.getAllUsers().size()).isEqualTo(1L);
        assertThat(response.getMessage()).contains("Successfully Registered");
    }

}