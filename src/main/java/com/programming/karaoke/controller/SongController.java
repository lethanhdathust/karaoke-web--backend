package com.programming.karaoke.controller;

import com.programming.karaoke.model.Song;
//import com.programming.karaoke.service.SongService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
//    @Autowired
//    private SongService songService;
//
//    @GetMapping("/getAllSong")
//    public ResponseEntity<List<Song>> getAllSongs() {
//        List<Song> songs = songService.getAllSongs();
//        return ResponseEntity.ok(songs);
//    }
//@PostMapping("createSong")
//public ResponseEntity<Song> createSong(@RequestParam("songBeat") MultipartFile songBeat,
//                                       @RequestParam("songImage") MultipartFile songImage,
//                                       @RequestParam("songVideo") MultipartFile songVideo,
//                                       @RequestParam("lyric") MultipartFile lyric,
//                                       @RequestParam("title") String title,
//                                       @RequestParam("artist") String artist,
//                                       @RequestParam("genre") String genre) {
//    try {
//        Song song = songService.createSong(title, artist, genre, songImage, songBeat, songVideo, lyric);
//        return ResponseEntity.ok(song);
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//}
//
//
//    @PutMapping("/udpate/{songId}")
//    public ResponseEntity<Song> updateSong(@PathVariable String songId,
//                                           @RequestParam(required = false) String title,
//                                           @RequestParam(required = false) String artist,
//                                           @RequestParam(required = false) String genre,
//                                           @RequestParam(required = false) MultipartFile songImage,
//                                           @RequestParam(required = false) MultipartFile songBeat,
//                                           @RequestParam(required = false) MultipartFile songVideo,
//                                           @RequestParam(required = false) MultipartFile lyric) {
//        try {
//            Song updatedSong = songService.updateSong(songId, title, artist, genre, songImage, songBeat, songVideo, lyric);
//            return ResponseEntity.ok(updatedSong);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @DeleteMapping("/delete/{songId}")
//    public ResponseEntity<Void> deleteSong(@PathVariable String songId) {
//        try {
//            songService.deleteSong(songId);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/upload")
    public ResponseEntity<Song> uploadSong(@RequestParam("title") String title,
                                           @RequestParam("artist") String artist,
                                           @RequestParam("genre") String genre,
                                           @RequestParam("image") MultipartFile imageFile,
                                           @RequestParam("video") MultipartFile videoFile) {
        try {
            // Save image and video files to the server's file system
            String imagePath = saveFile(imageFile, "./songs/images");
            String videoPath = saveFile(videoFile, "./songs/videos");

            // Insert song data into the MongoDB database
            Song song = new Song(title, artist, genre, imagePath, videoPath);
            song = mongoTemplate.insert(song, "songs");
            return ResponseEntity.ok(song);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = mongoTemplate.findAll(Song.class, "songs");
        return ResponseEntity.ok(songs);
    }

    private String saveFile(MultipartFile file, String directoryPath) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = directoryPath + "/" + fileName;
        File dest = new File(filePath);
        FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
        return filePath;
    }

}
