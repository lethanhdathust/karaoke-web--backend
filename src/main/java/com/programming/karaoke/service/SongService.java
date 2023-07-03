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
@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Value("${song.folder.path}")
    private String songFolderPath;

    public void createSongFolder(String songName) {
        String songFolderName = songName.replaceAll("\\s+", ""); // Remove spaces from song name
        File songFolder = new File(songFolderPath + File.separator + songFolderName);
        if (!songFolder.exists()) {
            songFolder.mkdirs();
        }
    }

    public void saveFiles(String songName, MultipartFile soundFile) throws IOException {
        String songFolderName = songName.replaceAll("\\s+", ""); // Remove spaces from song name
        String soundFileName = soundFile.getOriginalFilename();

        File songsFolder = new File(songFolderPath);
        if (!songsFolder.exists()) {
            songsFolder.mkdirs();
        }

        String songFolderFullPath = songsFolder.getAbsolutePath() + File.separator + songFolderName;

        File songFolder = new File(songFolderFullPath);
        if (!songFolder.exists()) {
            songFolder.mkdirs();
        }

        File soundFilePath = new File(songFolder.getAbsolutePath() + File.separator + soundFileName);
        soundFile.transferTo(soundFilePath);

        String beatFileName = "wav_sound"; // Set the beat file name
        MultipartFile beatFile = new MockMultipartFile(beatFileName, beatFileName, null, new FileInputStream(new File("wav_sound" + File.separator + beatFileName)));
        saveBeatFile(songFolderFullPath, beatFile);

        String lyricFileName = soundFileName.substring(0, soundFileName.lastIndexOf('.')) + ".lyric";
        MultipartFile lyricFile = new MockMultipartFile(lyricFileName, lyricFileName, null, new FileInputStream(new File("lyric" + File.separator + lyricFileName)));
        saveLyricFile(songFolderFullPath, lyricFile);

        saveSongToDatabase(songName, songFolderName);
    }

    private void saveLyricFile(String songFolderFullPath, MultipartFile lyricFile) throws IOException {
        // Save the lyric file to the song folder
        File lyricFilePath = new File(songFolderFullPath + File.separator + lyricFile.getOriginalFilename());
        lyricFile.transferTo(lyricFilePath);
    }

    private void saveBeatFile(String songFolderFullPath, MultipartFile beatFile) throws IOException {
        // Save the beat file to the song folder
        File beatFilePath = new File(songFolderFullPath + File.separator + beatFile.getOriginalFilename());
        beatFile.transferTo(beatFilePath);
    }

    private void saveSongToDatabase(String songName, String songFolderName) {
        // Save the song details to the database using the SongRepository
        Song song = new Song();
        song.setSongName(songName);
        song.setSongBeat(songFolderName); // Use the songBeat field to store the song folder name
        // Set other song details as needed
        songRepository.save(song);
    }
}
