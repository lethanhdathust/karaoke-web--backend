package com.programming.karaoke.controller;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;

public ResponseEntity<Song> createSong(@RequestParam("songBeat") MultipartFile songBeat,
                                       @RequestParam("songImage") MultipartFile songImage,
                                       @RequestParam("songVideo") MultipartFile songVideo,
                                       @RequestParam("lyric") MultipartFile lyric,
                                       String title,
                                       String artist,
                                       String genre) {
    try {
        Song song = songService.createSong(title, artist, genre, songImage, songBeat, songVideo, lyric);
        return ResponseEntity.ok(song);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


    @PutMapping("/songs/{songId}")
    public ResponseEntity<Song> updateSong(@PathVariable String songId,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String artist,
                                           @RequestParam(required = false) String genre,
                                           @RequestParam(required = false) MultipartFile songImage,
                                           @RequestParam(required = false) MultipartFile songBeat,
                                           @RequestParam(required = false) MultipartFile songVideo,
                                           @RequestParam(required = false) MultipartFile lyric) {
        try {
            Song updatedSong = songService.updateSong(songId, title, artist, genre, songImage, songBeat, songVideo, lyric);
            return ResponseEntity.ok(updatedSong);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
