package com.programming.karaoke.model;

import com.programming.karaoke.model.Comment;
import com.programming.karaoke.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
//@Data là một annotation khác của Lombok cung cấp các chức năng tương tự như @Getter và @Setter,
// nhưng nó tự động tạo ra các phương thức
// getter và setter, cũng như phương thức equals(), hashCode(), và toString() trong một lớp đối tượng.
@Data
@Document(value="Videos")
//@NoArgsConstructor sẽ tự động tạo ra một
// constructor không tham số cho lớp đối tượng. Ví dụ:
@NoArgsConstructor
//@AllArgsConstructor sẽ tự động tạo ra một constructor
//  chứa tất cả các thuộc tính của lớp đối tượng.
@AllArgsConstructor
public class VideoDto {
  private String id;
  private String title;
  private String description;
  private String userId;
  private AtomicInteger likes ;
  private AtomicInteger disLikes = new AtomicInteger(0);
  private List<String> tags;
  private String url;
  private VideoStatus videoStatus;
  private AtomicInteger viewCount = new AtomicInteger(0);
  private String thumbnailUrl;
  private List<Comment> comments = new ArrayList<>();
}
