package com.programming.karaoke.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
@Data
public class Comment {
    @Id
    private String id;
    private String text;
    private String authorId;
    private Integer likeCount;
    private Integer disLikeCount;
}
