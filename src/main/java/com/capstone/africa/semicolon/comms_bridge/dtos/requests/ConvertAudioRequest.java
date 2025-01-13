package com.capstone.africa.semicolon.comms_bridge.dtos.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertAudioRequest {
    private Long sessionId;
    private String audioUrl;
}
