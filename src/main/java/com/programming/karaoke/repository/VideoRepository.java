package com.programming.karaoke.repository;

import com.programming.karaoke.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository  extends MongoRepository<Video, String> {
//    The first generic type parameter Video specifies the entity type that the repository will work with,
//    meaning that the repository will be able to perform CRUD operations on objects of type Video.
//
//The second generic type parameter String specifies the type of the ID field of the Video entity.
// In this case, it is a String, indicating that the ID field of the Video entity is of type String.


// Lớp chứa các phương thức khi giao tiếp vơi database


    List<Video> search(String tilte, String description, String tag);
}
