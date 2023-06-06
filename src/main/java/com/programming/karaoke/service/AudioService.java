package com.programming.karaoke.service;

import com.programming.karaoke.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudioService {

    @Autowired
    private AudioRepository audioRepository;

    public void convertVideoToAudio(String videoFilePath, String audioFilePath )
}
