package com.programming.karaoke.repository;

import com.programming.karaoke.model.Recordings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordingRepository extends MongoRepository<Recordings,String> {
}
