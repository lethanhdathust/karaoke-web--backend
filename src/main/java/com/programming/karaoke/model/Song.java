package com.programming.karaoke.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "song")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    private String songId;
    private String songName;
    private byte[] songImg;
    private String genre;
    private String songFolderName;
    private byte[] songLyric;
    private Long songDuration;
    private String artist;
    private byte[] songBeat;
    private String songFormat;
}
