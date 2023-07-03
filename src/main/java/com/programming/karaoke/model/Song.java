package com.programming.karaoke.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "song")
public class Song {
    @Id
    private String songId;
    private String songName;
    private String songImg;
    private String songLyric;
    private Long songDuration;
    private String artist;
    private String songBeat;
    private String songFormat;
}
