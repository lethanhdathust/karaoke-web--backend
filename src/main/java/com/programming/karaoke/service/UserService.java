package com.programming.karaoke.service;

import com.programming.karaoke.model.user.User;
import com.programming.karaoke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User updateUser(User userRequest) {
        User existingUser = userRepository.findById(userRequest.getId()).get();
        existingUser.setId(userRequest.getId());
        existingUser.setFullName(userRequest.getFullName());
        existingUser.setEmailAddress(userRequest.getEmailAddress());
        existingUser.setTelephoneNumber(userRequest.getTelephoneNumber());
        return userRepository.save(existingUser);
    }
}
