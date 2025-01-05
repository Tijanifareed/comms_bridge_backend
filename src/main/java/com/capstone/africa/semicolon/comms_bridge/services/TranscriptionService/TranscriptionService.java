package com.capstone.africa.semicolon.comms_bridge.services.TranscriptionService;

import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ConvertAudioRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ConvertAudioResponse;

public interface TranscriptionService {
    ConvertAudioResponse convertAudioToText(ConvertAudioRequest request);
}
