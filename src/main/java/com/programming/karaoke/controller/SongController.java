package com.programming.karaoke.controller;

import com.programming.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSong(@RequestParam("soundFile") MultipartFile soundFile) {
        try {
            String songName = StringUtils.stripFilenameExtension(soundFile.getOriginalFilename());
            songService.createSongFolder(songName);
            songService.saveFiles(songName, soundFile);
            return ResponseEntity.ok("Song uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload song.");
        }
    }
}
