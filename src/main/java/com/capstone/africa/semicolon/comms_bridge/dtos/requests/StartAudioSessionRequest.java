package com.capstone.africa.semicolon.comms_bridge.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@ToString
public class StartAudioSessionRequest {
    private Long userId;
    private MultipartFile audioFile;

}
