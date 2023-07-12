package com.programming.karaoke.repository;

import com.programming.karaoke.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
    List<Song> findByTitleContainingIgnoreCase(String keyword);
    List<Song> findByTitleAndArtist(String title, String artist);
    List<Song> findByGenre(String genre);
    Optional<Song> findByTitle(String title);
}
