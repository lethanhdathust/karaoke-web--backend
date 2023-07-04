package com.programming.karaoke.service;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public void createSong(MultipartFile songFile, MultipartFile imgFile, MultipartFile lyricFile) {
        String originalSongFilename = songFile.getOriginalFilename();
        String songName = originalSongFilename.substring(0, originalSongFilename.lastIndexOf("."));

        String songFolderName = "songs" + File.separator + songName;
        createSongFolderIfNotExists(songFolderName);

        String originalImgFilename = imgFile.getOriginalFilename();
        String imgFilename = originalImgFilename != null ? originalImgFilename : "songImg.jpg";
        saveFile(imgFile, songFolderName, imgFilename);

        String originalLyricFilename = lyricFile.getOriginalFilename();
        String lyricFilename = originalLyricFilename != null ? originalLyricFilename : "songLyric.txt";
        saveFile(lyricFile, songFolderName, lyricFilename);

        Song song = new Song();
        song.setSongName(songName);
        song.setSongFolderName(songFolderName);
        // Set other song details as needed
        songRepository.save(song);
    }

    private void createSongFolderIfNotExists(String songFolderName) {
        File songFolder = new File(songFolderName);
        if (!songFolder.exists()) {
            songFolder.mkdirs();
        }
    }

    private void saveFile(MultipartFile file, String songFolderName, String fileName) {
        try {
            String filePath = songFolderName + File.separator + fileName;
            File targetFile = new File(filePath);
            file.transferTo(targetFile);
        } catch (IOException e) {
            // Handle any exceptions
        }
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
}
