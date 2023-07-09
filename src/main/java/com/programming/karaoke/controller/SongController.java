package com.programming.karaoke.controller;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping("/getAllSong")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("getSongById/{id}")
    public Song getSongById(@PathVariable("id") String id) {
        return songService.getSongById(id);
    }

    @PostMapping("createSong")
    public Song createSong(@RequestPart("songImage") MultipartFile songImage,
                           @RequestPart("songVideo") MultipartFile songVideo,
                           @RequestPart("songBeat") MultipartFile songBeat,
                           @RequestPart("song") Song song) {
        song.setSongImage(songImage);
        song.setSongVideo(songVideo);
        song.setSongBeat(songBeat);
        return songService.createSong(song);
    }

    @PutMapping("updateSong/{id}")
    public Song updateSong(@PathVariable("id") String id,
                           @RequestPart(value = "songImage", required = false) MultipartFile songImage,
                           @RequestPart(value = "songVideo", required = false) MultipartFile songVideo,
                           @RequestPart(value = "songBeat", required = false) MultipartFile songBeat,
                           @RequestPart("song") Song song) {
        song.setSongImage(songImage);
        song.setSongVideo(songVideo);
        song.setSongBeat(songBeat);
        return songService.updateSong(id, song);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable("id") String id) {
        songService.deleteSong(id);
        return ResponseEntity.ok("Song deleted successfully");
    }
}
