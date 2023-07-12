package com.programming.karaoke.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "song")
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    private String id;
    private String title;
    private String artist;
    private String genre;
    private String imageUrl;
    private String videoUrl;
    private String songUrl;
}
