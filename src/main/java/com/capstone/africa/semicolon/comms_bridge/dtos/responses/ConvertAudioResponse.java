package com.capstone.africa.semicolon.comms_bridge.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConvertAudioResponse {
    private String transcriptText;
    private Long transcriptionId;
    private String status;

}