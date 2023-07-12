package com.programming.karaoke.service;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private S3Service s3Service;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(String id) {
        return songRepository.findById(id).orElse(null);
    }

    public List<Song> searchSongs(String query) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(query, query);
    }
    public List<Song> getSongsByGenre(String genre) {
        return songRepository.findByGenre(genre);
    }

    public Song createSong(Song song, MultipartFile imageFile, MultipartFile videoFile) throws IOException {
        String imageUrl = s3Service.uploadFile(imageFile);
        String videoUrl = s3Service.uploadFile(videoFile);
        song.setImageUrl(imageUrl);
        song.setVideoUrl(videoUrl);
        return songRepository.save(song);
    }

    public Song updateSong(String id, Song songDetails) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            return null;
        }
        song.setTitle(songDetails.getTitle());
        song.setArtist(songDetails.getArtist());
        song.setGenre(songDetails.getGenre());
        return songRepository.save(song);
    }

    public boolean deleteSong(String id) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) {
            return false;
        }
        songRepository.delete(song);
        return true;
    }
}
