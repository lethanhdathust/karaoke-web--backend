package com.programming.karaoke.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "recordings")


public class Recordings {

    @Id
    private long id;
    private String name;
    private String format;
    private String filePath;
    private Long duration;
    private byte[] bytes;
    private String fileFormat;


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getFileFormat() {
//        return fileFormat;
//    }
//
//    public void setFileFormat(String fileFormat) {
//        this.fileFormat = fileFormat;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getFormat() {
//        return format;
//    }
//
//    public void setFormat(String format) {
//        this.format = format;
//    }
//
//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public Long getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Long duration) {
//        this.duration = duration;
//    }
//
//    public byte[] getBytes() {
//        return bytes;
//    }
//
//    public void setBytes(byte[] bytes) {
//        this.bytes = bytes;
//    }
}
