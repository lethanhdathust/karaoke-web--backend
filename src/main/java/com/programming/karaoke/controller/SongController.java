package com.programming.karaoke.controller;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @PostMapping("/create")
    public ResponseEntity<String> createSong(
            @RequestPart("songFile") MultipartFile songFile,
            @RequestPart(value = "imgFile", required = false) MultipartFile imgFile,
            @RequestPart(value = "lyricFile", required = false) MultipartFile lyricFile,
            @RequestBody Song song
    ) {
        try {
            songService.createSong(songFile, imgFile, lyricFile, song);
            return ResponseEntity.status(HttpStatus.CREATED).body("Song created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create song.");
        }
    }

    @GetMapping("/getAllSongs")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }
}
