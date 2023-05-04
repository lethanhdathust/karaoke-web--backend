package com.programming.karaoke.controller;

import com.programming.karaoke.repository.RecordingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

@RestController
@RequestMapping("/recordings")
public class RecordController {

    @Autowired
    private RecordingRepository recordingRepository;

    @PostMapping
    public ResponseEntity<?> createRecording(@RequestParam("file")MultipartFile file, @Value("${fileDir}") String fileDir) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getInputStream());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
}
