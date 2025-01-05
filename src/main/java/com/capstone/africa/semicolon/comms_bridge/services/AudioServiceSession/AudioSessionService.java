package com.capstone.africa.semicolon.comms_bridge.services.AudioServiceSession;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.StartAudioSessionRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.StartAudioSessionResponse;

import java.io.IOException;

public interface AudioSessionService {
    StartAudioSessionResponse startAudioSession(StartAudioSessionRequest request) throws IOException;

}
