package com.programming.karaoke.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.programming.karaoke.model.Song;
//import com.programming.karaoke.service.SongService;
import com.programming.karaoke.service.SongService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;


    @GetMapping("/getAllSongs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("getSong/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable String id) {
        Song song = songService.getSongById(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(song);
    }

    @PostMapping("/add")
    public ResponseEntity<Song> createSong(@ModelAttribute Song song, @RequestParam("image") MultipartFile imageFile, @RequestParam("video") MultipartFile videoFile) throws IOException {
        Song savedSong = songService.createSong(song, imageFile, videoFile);
        try {
            return ResponseEntity.created(new URI("/songs/" + savedSong.getId())).body(savedSong);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to create URI for song", e);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable String id, @ModelAttribute Song song) {
        Song updatedSong = songService.updateSong(id, song);
        if (updatedSong == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable String id) {
        boolean deleted = songService.deleteSong(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
