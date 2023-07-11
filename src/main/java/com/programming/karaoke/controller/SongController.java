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
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;


    @GetMapping("/getAllSongs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @PostMapping(value = "/add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void addSong(@RequestParam("image") MultipartFile imageFile,
                        @RequestParam("video") MultipartFile videoFile,
                        @RequestParam("song") String songJson) throws IOException {
        songService.addSong(imageFile, videoFile, songJson);

    }
    @PutMapping(value = "update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void updateSong(@PathVariable String id,
                           @RequestParam(value = "image", required = false) File imageFile,
                           @RequestParam(value = "video", required = false) File videoFile,
                           @RequestParam("song") String songJson) throws IOException {
        songService.updateSong(id, imageFile, videoFile, songJson);
    }

    @DeleteMapping("delete/{id}")
    public void deleteSong(@PathVariable String id) {
        songService.deleteSong(id);
    }
}
