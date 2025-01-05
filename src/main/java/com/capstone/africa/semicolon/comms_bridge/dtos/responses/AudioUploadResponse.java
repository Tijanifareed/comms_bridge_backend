package com.capstone.africa.semicolon.comms_bridge.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudioUploadResponse {
    public String public_id;
    public String format;
    public String url;
    public String secure_url;
}