package com.programming.karaoke.service;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public Song createSong(String title, String artist, String genre, MultipartFile songImage, MultipartFile songBeat, MultipartFile songVideo, MultipartFile lyric) throws Exception {

        Song song = new Song();
        song.setArtist(artist);
        song.setTitle(title);
        song.setGenre(genre);
        song.setLyric(lyric);
        song.setSongVideo(songVideo);
        song.setSongBeat(songBeat);
        song.setSongImage(songImage);

        songRepository.save(song);
        return  song;
    }
    public Optional<Song> getSongById(String songId) { return songRepository.findById(songId);}

    public void deleteRecordingById( String songId) {
        songRepository.deleteById(songId);
    }

    public Song updateSong(String songId, String title, String artist, String genre, MultipartFile songImage, MultipartFile songBeat, MultipartFile songVideo, MultipartFile lyric) throws Exception {
        Optional<Song> optionalSong = songRepository.findById(songId);

        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();

            if (title != null) {
                // Check if there is another song with the same title
                Optional<Song> optionalSongWithTitle = songRepository.findByTitle(title);
                if (optionalSongWithTitle.isPresent() && !optionalSongWithTitle.get().getSongId().equals(songId)) {
                    throw new Exception("Another song with the same title already exists");
                }

                song.setTitle(title);
            }

            if (artist != null) {
                song.setArtist(artist);
            }

            if (genre != null) {
                song.setGenre(genre);
            }

            if (songImage != null) {
                song.setSongImage(songImage);
            }

            if (songBeat != null) {
                song.setSongBeat(songBeat);
            }

            if (songVideo != null) {
                song.setSongVideo(songVideo);
            }
            if (lyric != null) {
                song.setLyric(lyric);
            }

            songRepository.save(song);

            return song;
        } else {
            throw new Exception("Song not found");
        }
    }
}
