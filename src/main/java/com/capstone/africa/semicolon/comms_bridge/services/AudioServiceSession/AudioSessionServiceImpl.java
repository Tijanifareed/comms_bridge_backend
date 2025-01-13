package com.capstone.africa.semicolon.comms_bridge.services.AudioServiceSession;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ConvertAudioRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.StartAudioSessionRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.AudioUploadResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ConvertAudioResponse;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.StartAudioSessionResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.AudioSession;
import com.capstone.africa.semicolon.comms_bridge.repositories.AudioSessionRepository;
import com.capstone.africa.semicolon.comms_bridge.services.TranscriptionService.TranscriptionService;
import com.capstone.africa.semicolon.comms_bridge.services.audioService.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
@Service
public class AudioSessionServiceImpl implements AudioSessionService {

    @Autowired
    private AudioService audioService;

    @Autowired
    private TranscriptionService transcriptionService;

    @Autowired
    private AudioSessionRepository audioSessionRepository;

    @Override
    public StartAudioSessionResponse startAudioSession(StartAudioSessionRequest request) throws IOException {
        // Step 1: Upload audio and get audio URL
        AudioUploadResponse audioUrl = audioService.uploadAudio(request.getAudioFile());

        // Step 2: Create and save audio session
        AudioSession audioSession = new AudioSession();
        audioSession.setUserId(request.getUserId());
        audioSession.setAudioUrl(audioUrl.getUrl());
        audioSession.setStatus("Processing");
        audioSession.setStartedAt(LocalDateTime.now());
        audioSession.setAudioFormat(audioUrl.getFormat());
        AudioSession savedAudioSession = audioSessionRepository.save(audioSession);

        // Step 3: Request transcription
        ConvertAudioRequest convertAudioRequest = new ConvertAudioRequest();
        convertAudioRequest.setAudioUrl(savedAudioSession.getAudioUrl());
        convertAudioRequest.setSessionId(savedAudioSession.getId());

        ConvertAudioResponse transcriptionResponse = transcriptionService.convertAudioToText(convertAudioRequest);

        // Step 4: Update audio session with transcription details
        savedAudioSession.setTranscriptionId(transcriptionResponse.getTranscriptionId());
        savedAudioSession.setStatus("Completed");
        audioSessionRepository.save(savedAudioSession);

        // Step 5: Build response
        StartAudioSessionResponse response = new StartAudioSessionResponse();
        response.setSessionId(savedAudioSession.getId());
        response.setAudioUrl(savedAudioSession.getAudioUrl());
        response.setTranscriptionText(transcriptionResponse.getTranscriptText());
        response.setStatus(savedAudioSession.getStatus());

        return response;
    }
}

