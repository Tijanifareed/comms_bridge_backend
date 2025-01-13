package com.capstone.africa.semicolon.comms_bridge.controllers;


import com.capstone.africa.semicolon.comms_bridge.dtos.requests.StartAudioSessionRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ApiResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.StartAudioSessionResponse;
import com.capstone.africa.semicolon.comms_bridge.services.AudioServiceSession.AudioSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AudioSessionController {
    @Autowired
    private AudioSessionService audioSessionService;
//


    @PostMapping("/transcribe-audio")
    public ResponseEntity<?> startAudioSession(@RequestParam("audio") MultipartFile audioFile,
                                               @RequestParam("userId") Long userId) {
        try {
            // Log the file information for debugging
            System.out.println("Received audio file for transcription: " + audioFile.getOriginalFilename());
            System.out.println("Audio file size: " + audioFile.getSize()); // Log the file size

            if (audioFile.isEmpty()) {
                throw new RuntimeException("Uploaded file is empty.");
            }

            // Create the request object for service
            StartAudioSessionRequest request = new StartAudioSessionRequest();
            request.setUserId(userId);
            request.setAudioFile(audioFile);

            // Process the audio session
            StartAudioSessionResponse response = audioSessionService.startAudioSession(request);

            System.out.println(response);
            // Return success response
            System.out.println(ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, response).toString()));

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, response));

        } catch (IOException e) {
            // Handle upload failure
            System.err.println("Audio upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Audio upload failed. Please try again."));
        } catch (RuntimeException e) {
            // Handle runtime errors
            System.err.println("Error during audio processing: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }



}
