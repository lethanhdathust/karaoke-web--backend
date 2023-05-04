package com.programming.karaoke.controller;


import com.programming.karaoke.model.VideoDto;
import com.programming.karaoke.service.VideoServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class VideoController {
    private final VideoServices videoServices;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file")MultipartFile file)
    {
        videoServices.uploadVideo(file);
    }
    @PutMapping()
      @ResponseStatus(HttpStatus.OK)
      public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto){

        return  videoServices.editVideo(videoDto);
//        return videoServices.saveVideo(videoDto);
  //      return videoDto;
      }
}
