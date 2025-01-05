package com.capstone.africa.semicolon.comms_bridge.services.audioService;

import com.capstone.africa.semicolon.comms_bridge.dtos.responses.AudioUploadResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class AudioService {
    private final Cloudinary cloudinary;

    public AudioService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public AudioUploadResponse uploadAudio(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));  // Automatically detects resource type, including audio
        AudioUploadResponse response = new AudioUploadResponse();
        response.setPublic_id(uploadResult.get("public_id").toString());
        response.setFormat(uploadResult.get("format").toString());
        response.setUrl(uploadResult.get("url").toString());
        response.setSecure_url(uploadResult.get("secure_url").toString());


        return response;
    }
}

