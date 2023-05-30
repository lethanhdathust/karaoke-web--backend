package com.programming.karaoke.controller;


import com.programming.karaoke.model.Video;
import com.programming.karaoke.model.VideoDto;
import com.programming.karaoke.service.VideoServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/videos")
public class VideoController {
    private final VideoServices videoServices;
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file")MultipartFile file)
    {
        videoServices.uploadVideo(file);
    }
    @PutMapping("/edit")
      @ResponseStatus(HttpStatus.OK)
      public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto){

        return  videoServices.editVideo(videoDto);
//        return videoServices.saveVideo(videoDto);
  //      return videoDto;
      }

    @GetMapping("/search")
    public ResponseEntity<List<Video>> search(@RequestParam String query) {
        List<Video> videos = videoServices.search(query);
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }


}
