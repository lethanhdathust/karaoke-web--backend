package com.programming.karaoke.service;

import com.programming.karaoke.model.Recordings;
import com.programming.karaoke.model.Video;
import com.programming.karaoke.repository.RecordingRepository;
import org.apache.commons.codec.EncoderException;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import ws.schild.jave.AudioAttributes;
//import ws.schild.jave.Encoder;
//import ws.schild.jave.EncoderException;
//import ws.schild.jave.EncodingAttributes;
//import ws.schild.jave.MultimediaObject;


import javax.sound.sampled.*;
import java.io.*;
import java.util.Optional;
import java.util.UUID;

@Service
    public class RecordService {
        @Autowired
        private RecordingRepository recordingRepository;
        @Value("${fileDir}")
        private String fileDir;
        @Autowired
        private S3Service s3Service;
        public Recordings createRecording(MultipartFile file) throws Exception {

//            //Conn to AWS
//
//            String videoUrl= s3Service.uploadFile(file);
//            //Sau khi upload video lên aws , set url cho video ,rồi lưu vào database
//            var recordings = new Recordings();
//            recordings.set;
//            recordingRepository.save(recordings);

                //Check if id is existed or not
//            Optional<Recordings> existingRecordOptional = recordingRepository.findById(0L);
//            if (existingRecordOptional.isPresent()) {
//                // Update the existing document with the new file bytes
//                Recordings existingRecord = existingRecordOptional.get();
//                existingRecord.setBytes(file.getBytes());
//                recordingRepository.save(existingRecord);
//                return existingRecord;
//            } else {
                InputStream bufferedStream = new BufferedInputStream(file.getInputStream());
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
                AudioFormat format = audioInputStream.getFormat();
                long duration = (long) (audioInputStream.getFrameLength() / format.getFrameRate() * 1000);
                System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
                //Create Directory
                File directory = new File(fileDir);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs();
                    if (!created) {
                        throw new IllegalStateException("Failed to create the directory: " + fileDir);
                    }
                }
                String name = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                Recordings recordings = new Recordings();
                recordings.setName(name);
                recordings.setFormat(String.valueOf(format));
                recordings.setDuration(duration);
                recordings.setBytes(bytes);
                recordings.setFilePath(fileDir + File.separator + name);

                Tika tika = new Tika();
                String fileType = tika.detect(bytes);
                if (fileType.startsWith("audio/")) {
                    recordings.setFileFormat(fileType.substring("audio/".length()));
                } else {
                    throw new IllegalArgumentException("Not an audio file: " + fileType);
                }



                recordingRepository.save(recordings);
                return recordings;


            }
//        }



        public Optional<Recordings> getRecordingById(Long id) {
            return recordingRepository.findById(id);
        }

        public void deleteRecordingById( Long id) {
                recordingRepository.deleteById(id);

        }

        public byte[] getRecordingBytes(Recordings recordings) throws Exception {
            return  recordings.getBytes();
        }

//    public void mergeAudio(String recordedSoundPath, String extractedAudioPath, String outputFilePath) throws EncoderException {
//        MultimediaObject recordedSound = new MultimediaObject(new File(recordedSoundPath));
//        MultimediaObject extractedAudio = new MultimediaObject(new File(extractedAudioPath));
//
//        AudioAttributes audioAttributes = new AudioAttributes();
//        audioAttributes.setVolume(1.0);
//
//        EncodingAttributes encodingAttributes = new EncodingAttributes();
//        encodingAttributes.setOutputFormat("mp3");
//        encodingAttributes.setAudioAttributes(audioAttributes);
//
//        Encoder encoder = new Encoder();
//        encoder.encode(recordedSound, extractedAudio, new File(outputFilePath), encodingAttributes);
//    }
    }























