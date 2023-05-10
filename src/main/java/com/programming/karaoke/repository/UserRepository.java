package com.programming.karaoke.repository;

import com.programming.karaoke.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
