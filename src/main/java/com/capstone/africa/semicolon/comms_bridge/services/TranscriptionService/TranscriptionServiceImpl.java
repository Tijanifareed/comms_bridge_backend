package com.capstone.africa.semicolon.comms_bridge.services.TranscriptionService;

import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptOptionalParams;
import com.capstone.africa.semicolon.comms_bridge.dtos.requests.ConvertAudioRequest;
import com.capstone.africa.semicolon.comms_bridge.dtos.responses.ConvertAudioResponse;
import com.capstone.africa.semicolon.comms_bridge.entities.Transcription;
import com.capstone.africa.semicolon.comms_bridge.repositories.TranscriptionRepository;
import org.springframework.beans.factory.annotation.Value;
import com.assemblyai.api.AssemblyAI;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TranscriptionServiceImpl implements TranscriptionService {
    @Value("${assemblyai.api_key}")
    private String apiKey;

    private final TranscriptionRepository transcriptionRepository;

    public TranscriptionServiceImpl(TranscriptionRepository transcriptionRepository) {
        this.transcriptionRepository = transcriptionRepository;
    }

    @Override
    public ConvertAudioResponse convertAudioToText(ConvertAudioRequest request) {
        AtomicReference<String> transcriptedText = new AtomicReference<>("");
        AssemblyAI assemblyAI = AssemblyAI.builder()
                .apiKey(apiKey)
                .build();
        String url = request.getAudioUrl();
        var config = TranscriptOptionalParams.builder()
                .speakerLabels(true)
                .build();

        try {
            Transcript transcript = assemblyAI.transcripts().transcribe(url, config);
            transcript.getUtterances().ifPresent(utterances -> {
                utterances.forEach(utterance -> {
                    // Use formatTextToConversation to improve message structure
                    String formattedText = formatTextToConversation(utterance.getText());
                    transcriptedText.updateAndGet(text -> text + formattedText + "\n");
                });
            });

            Transcription transcription = new Transcription();
            transcription.setText(transcriptedText.get());
            transcription.setStatus("Created");
            transcription.setCreatedAt(LocalDateTime.now());
            transcription.setSessionId(request.getSessionId());
            Transcription transcription1 = transcriptionRepository.save(transcription);
            ConvertAudioResponse response = new ConvertAudioResponse();
            response.setStatus("success");
            response.setTranscriptText(transcriptedText.get());
            response.setTranscriptionId(transcription1.getId());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error transcribing voice", e);
        }
    }


    private String formatTextToConversation(String text) {
        String[] sentences = text.split("\\.\\s*|\\?\\s*|!\\s*");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < sentences.length; i++) {
            if (!sentences[i].trim().isEmpty()) {
                formatted.append("Message ").append(i + 1).append(": ")
                        .append(sentences[i].trim())
                        .append(".\n");
            }
        }
        return formatted.toString();
    }
}
