package com.programming.karaoke.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audios")
public class Audio {
    @Id
    private String id;
    private String fileName;



}

