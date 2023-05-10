package com.programming.karaoke.service;

import com.programming.karaoke.model.Video;
import com.programming.karaoke.model.VideoDto;
import com.programming.karaoke.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.modelmapper.ModelMapper;

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
//    Upload file to aws s3
   String videoUrl= s3Service.uploadFile(multipartFile);
   //Sau khi upload video lên aws , set url cho video ,rồi lưu vào database
   var video = new Video();
   video.setUrl(videoUrl);

   videoRepository.save(video);


}

    public List<VideoDto> searchVideos(String title, String description, String tag) {
        // Build a criteria object based on the search parameters
        Criteria criteria = new Criteria();

        if (title != null) {
            criteria.and("title").regex(title);
        }

        if (description != null) {
            criteria.and("description").regex(description);
        }

        if (tag != null) {
            criteria.and("tags").is(tag);
        }

        // Use the criteria to create a query and execute it against the videoRepository
        Query query = new Query(criteria);
        List<Video> matchingVideos = mongoTemplate.find(query, Video.class);

        // Convert the Video objects to VideoDto objects and return them
        return matchingVideos.stream()
                .map(video -> modelMapper.map(video, VideoDto.class))
                .collect(Collectors.toList());
    }


}
