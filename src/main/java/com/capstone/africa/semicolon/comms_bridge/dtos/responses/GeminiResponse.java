package com.capstone.africa.semicolon.comms_bridge.dtos.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class GeminiResponse {
    private String role;
    private String content;
}
