//package com.programming.karaoke.service;
//
//import com.programming.karaoke.model.Song;
//import com.programming.karaoke.repository.SongRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.apache.tools.ant.types.resources.MultiRootFileSet.SetType.file;
//
//@Service
//public class SongService {
//    @Autowired
//    private SongRepository songRepository;
//
//
//    public Song createSong(String title,
//                           String artist,
//                           String genre,
//                           MultipartFile songImage,
//                           MultipartFile songBeat,
//                           MultipartFile songVideo,
//                           MultipartFile lyric) throws Exception {
//
//
//        Song song = new Song();
//        song.setArtist(artist);
//        song.setTitle(title);
//        song.setGenre(genre);
//        song.setLyric(lyric.getBytes());
//        song.setSongVideo(songVideo.getBytes());
//        song.setSongBeat(songBeat.getBytes());
//        song.setSongImage(songImage.getBytes());
//
//        songRepository.save(song);
//        return  song;
//    }
//    public Optional<Song> getSongById(String songId) { return songRepository.findById(songId);}
//    public List<Song> getAllSongs() {
//        return songRepository.findAll();
//    }
//    public void deleteSong(String songId) throws Exception {
//        Optional<Song> optionalSong = songRepository.findById(songId);
//
//        if (optionalSong.isPresent()) {
//            songRepository.delete(optionalSong.get());
//        } else {
//            throw new Exception("Song not found");
//        }
//    }
//    public Song updateSong(String songId, String title, String artist, String genre, MultipartFile songImage, MultipartFile songBeat, MultipartFile songVideo, MultipartFile lyric) throws Exception {
//        Optional<Song> optionalSong = songRepository.findById(songId);
//
//        if (optionalSong.isPresent()) {
//            Song song = optionalSong.get();
//
//            if (title != null) {
//                // Check if there is another song with the same title
//                Optional<Song> optionalSongWithTitle = songRepository.findByTitle(title);
//                if (optionalSongWithTitle.isPresent() && !optionalSongWithTitle.get().getSongId().equals(songId)) {
//                    throw new Exception("Another song with the same title already exists");
//                }
//
//                song.setTitle(title);
//            }
//
//            if (artist != null) {
//                song.setArtist(artist);
//            }
//
//            if (genre != null) {
//                song.setGenre(genre);
//            }
//
//            if (songImage != null) {
//                song.setSongImage(songImage.getBytes());
//            }
//
//            if (songBeat != null) {
//                song.setSongBeat(songBeat.getBytes());
//            }
//
//            if (songVideo != null) {
//                song.setSongVideo(songVideo.getBytes());
//            }
//            if (lyric != null) {
//                song.setLyric(lyric.getBytes());
//            }
//
//            songRepository.save(song);
//
//            return song;
//        } else {
//            throw new Exception("Song not found");
//        }
//    }
//}
