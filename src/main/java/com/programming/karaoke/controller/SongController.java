package com.programming.karaoke.controller;

import com.programming.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSong(@RequestParam("songName") String songName,
                                             @RequestParam("soundFile") MultipartFile soundFile) {
        try {
            songService.createSongFolder(songName);
            songService.saveFiles(songName, soundFile);
            return ResponseEntity.status(HttpStatus.OK).body("Song uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload song.");
        }
    }
}
