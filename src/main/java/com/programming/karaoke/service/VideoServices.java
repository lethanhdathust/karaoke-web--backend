package com.programming.karaoke.service;

import com.google.gson.Gson;
import com.programming.karaoke.model.Video;
import com.programming.karaoke.model.VideoDto;
import com.programming.karaoke.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ExecutableFindOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
@Service
//VideoServices chứa logic tải video
public class VideoServices {
private final S3Service s3Service;
private final VideoRepository videoRepository;

  public  VideoDto editVideo(VideoDto videoDto) {
//    Find the video by video
    var savedVideo =  videoRepository.findById(videoDto.getId()).orElseThrow(() -> new IllegalArgumentException("Cannot find video by id -"+ videoDto.getId()));
//    Map the videoDto field to video
    savedVideo.setTitle(videoDto.getTitle());
    savedVideo.setDescription(videoDto.getDescription());
    savedVideo.setTags(videoDto.getTags());
//    savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
    savedVideo.setVideoStatus(videoDto.getVideoStatus());
//save the viedo to database
    videoRepository.save(savedVideo);
    return videoDto;
  }
public VideoDto saveVideo(VideoDto videoDto, String id){
  var x =  videoRepository.findById(id);
  return videoDto;
}
  public void uploadVideo(MultipartFile multipartFile){

      //Upload file to aws s3
   String videoUrl= s3Service.uploadFile(multipartFile);
   //Sau khi upload video lên aws , set url cho video ,rồi lưu vào database
   var video = new Video();
   video.setUrl(videoUrl);
   videoRepository.save(video);


}

    public List<Video> search(String query) {
      Query searchQuery = new Query();
      searchQuery.addCriteria(Criteria.where("title").regex(query, "i")
              .orOperator(Criteria.where("description").regex(query, "i"),
                      Criteria.where("tags").in(query)));
      return videoRepository.findAll();
    }


}
