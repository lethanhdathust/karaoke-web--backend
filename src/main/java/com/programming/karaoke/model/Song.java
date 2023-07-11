package com.programming.karaoke.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Data
@Document(value = "song")
@NoArgsConstructor
@AllArgsConstructor

public class Song {
    @Id
    private String id;
    private String title;
    private String artist;
    private String genre;
    private byte[] imageUrl;
    private byte[] videoUrl;
    private String songUrl;

    public static Song fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Song.class);
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

}
