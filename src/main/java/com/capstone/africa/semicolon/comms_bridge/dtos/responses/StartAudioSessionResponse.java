package com.capstone.africa.semicolon.comms_bridge.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StartAudioSessionResponse {
    public Long sessionId;
    public String audioUrl;
    public String transcriptionText;
    public String status;
    public String message;
}
