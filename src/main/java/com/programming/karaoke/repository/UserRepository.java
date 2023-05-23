package com.programming.karaoke.repository;

import com.programming.karaoke.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByEmailAddress(String email);
}
