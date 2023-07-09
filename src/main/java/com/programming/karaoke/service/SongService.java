package com.programming.karaoke.service;

import com.amazonaws.services.kms.model.NotFoundException;
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
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public Song createSong(MultipartFile songImage, MultipartFile songBeat, MultipartFile songVideo) throws Exception {
        Song song = new Song();
        song.setArtist();
    }
}
