package com.programming.karaoke.repository;

import com.programming.karaoke.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
    List<Song> findByTitleContainingIgnoreCase(String keyword);
    List<Song> findByGenre(String genre);
    List<Song> findByTitleContainingIgnoreCaseAndGenre(String keyword, String genre);
}
