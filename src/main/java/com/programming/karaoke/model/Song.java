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

import java.io.IOException;
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
    private String imageUrl;
    private String videoUrl;

}



//    public static Song fromJson(String json) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(json, Song.class);
//    }
//
//    public String toJson() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(this);
//    }

