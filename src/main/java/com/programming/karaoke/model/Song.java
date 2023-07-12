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

import java.util.Base64;

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

    public String getImageUrlBase64() {
        return Base64.getEncoder().encodeToString(this.imageUrl);
    }

    public void setImageUrlBase64(String imageUrlBase64) {
        this.imageUrl = Base64.getDecoder().decode(imageUrlBase64);
    }
    public String getVideoUrlBase64() {
        return Base64.getEncoder().encodeToString(this.videoUrl);
    }
    public void setVideoUrlBase64(String imageUrlBase64) {
        this.videoUrl = Base64.getDecoder().decode(imageUrlBase64);
    }
}
