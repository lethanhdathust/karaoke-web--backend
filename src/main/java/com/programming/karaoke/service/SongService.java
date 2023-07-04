package com.programming.karaoke.service;

import com.programming.karaoke.model.Song;
import com.programming.karaoke.repository.SongRepository;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;
    private Long extractSongDurationFromBeat(byte[] songBeat) throws IOException {
        try (InputStream inputStream = new FileInputStream(Arrays.toString(songBeat))) {
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();
            parser.parse(inputStream, handler, metadata, parseContext);

            String durationString = metadata.get("xmpDM:duration");
            // Convert durationString to Long if needed

            // For demonstration purposes, let's assume the duration is 300 seconds
            return 300L;
        } catch (Exception e) {
            // Handle any exceptions or return a default duration value
            e.printStackTrace();
            return null;
        }
    }
    public void createSong(MultipartFile songFile, MultipartFile imgFile, MultipartFile lyricFile, Song song ) throws IOException {
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

        byte[] songBeat = songFile.getBytes();
        Long songDuration = extractSongDurationFromBeat(songBeat);

        Song song = new Song();

        song.setSongName(songName);
        song.setSongFolderName(songFolderName);
        song.setGenre(genre);
        song.setSongDuration(songDuration);
        song.setArtist(artist);
        song.setSongBeat(songBeat);


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
