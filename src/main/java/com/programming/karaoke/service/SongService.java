package com.programming.karaoke.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.GridFSBucket;
import com.programming.karaoke.model.Song;
import com.programming.karaoke.repository.SongRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.apache.tools.ant.types.resources.MultiRootFileSet.SetType.file;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private  S3Service s3Service;
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public void addSong(MultipartFile imageFile, MultipartFile videoFile, String songJson) throws IOException {
        Song song = Song.fromJson(songJson);

        InputStream imageStream = imageFile.getInputStream();
        InputStream videoStream = videoFile.getInputStream();
        // Create a new GridFS file for the image
        ObjectId imageUrl = gridFSBucket.uploadFromStream(imageFile.getOriginalFilename(), imageStream);

        // Create a new GridFS file for the video
        ObjectId videoUrl = gridFSBucket.uploadFromStream(videoFile.getOriginalFilename(), videoStream);

        song.setImageUrl(imageUrl.toByteArray());
        song.setVideoUrl(videoUrl.toByteArray());

        songRepository.save(song);

    }

    public void updateSong(String id, File imageFile, File videoFile, String songJson) throws IOException {
        Song song = Song.fromJson(songJson);
        if (imageFile != null && imageFile.exists()) {
            song.setImageUrl(Files.readAllBytes(imageFile.toPath()));
        } else {
            Song existingSong = songRepository.findById(id).orElse(null);
            song.setImageUrl(existingSong.getImageUrl());
        }
        if (videoFile != null && videoFile.exists()) {
            song.setVideoUrl(Files.readAllBytes(videoFile.toPath()));
        } else {
            Song existingSong = songRepository.findById(id).orElse(null);
            song.setVideoUrl(existingSong.getVideoUrl());
        }
        song.setId(id);
        songRepository.save(song);
    }

    public void deleteSong(String id) {
        songRepository.deleteById(id);
    }

    public List<Song> searchSongs(String title, String artist) {
        return songRepository.findByTitleAndArtist(title, artist);
    }
}
