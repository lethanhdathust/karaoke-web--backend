package com.programming.karaoke.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "recordings")
@NoArgsConstructor
@AllArgsConstructor
public class Recordings {

    @Id
    private long id;
    private String name;
    private String format;
    private String filePath;
    private Long duration;

}
