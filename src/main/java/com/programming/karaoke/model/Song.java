package com.programming.karaoke.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Data
@Document(value = "song")

@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    private String songId;
    private String title;
    private String artist;
    private MultipartFile lyric;
    private MultipartFile songImage;
    private MultipartFile songVideo;
    private MultipartFile songBeat;
    private String genre;

}
