package com.programming.karaoke.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(value="User")
//@NoArgsConstructor sẽ tự động tạo ra một
// constructor không tham số cho lớp đối tượng. Ví dụ:
@NoArgsConstructor
//@AllArgsConstructor sẽ tự động tạo ra một constructor
//  chứa tất cả các thuộc tính của lớp đối tượng.
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private Set<String> subscribedToUser;
    private Set<String> subscribers;
    private List<String> videoHistory;
    private Set<String> likedVideos;
    private Set<String> dislikeEdVideo;
}
