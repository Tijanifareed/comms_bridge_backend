package com.capstone.africa.semicolon.comms_bridge.controllers;


import com.capstone.africa.semicolon.comms_bridge.dtos.requests.GeminiRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ApiResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.AudioResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.GeminiResponse;
import com.capstone.africa.semicolon.comms_bridge.services.chatService.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class AiController {
    @Autowired
    private ChatService chatService;

    public AiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/summararize")
    public ResponseEntity<?> summarizeMessage(@RequestBody GeminiRequest request){
        try{
            System.out.println(request.toString());
            GeminiResponse response = chatService.summarizeMessage(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (IOException | InterruptedException e){
            return ResponseEntity.status(500).body(new AudioResponse("Audio upload failed"));
        }catch (RuntimeException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}
