package com.programming.karaoke.repository;

import com.programming.karaoke.model.Audio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AudioRepository extends MongoRepository<Audio, String> {
}
