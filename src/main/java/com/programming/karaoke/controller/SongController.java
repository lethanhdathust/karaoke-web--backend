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
    public ResponseEntity<String> createSong(@RequestParam("songFile") MultipartFile songFile,
                                             @RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
                                             @RequestParam(value = "lyricFile", required = false) MultipartFile lyricFile) {
        songService.createSong(songFile, imgFile, lyricFile);
        return ResponseEntity.ok("Song created successfully.");
    }

    @GetMapping("/getAllSongs")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }
}
