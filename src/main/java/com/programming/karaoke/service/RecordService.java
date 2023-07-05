package com.programming.karaoke.service;

import com.amazonaws.AmazonWebServiceRequest;
import com.programming.karaoke.model.Recordings;
import com.programming.karaoke.model.Video;
import com.programming.karaoke.repository.RecordingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
import java.util.Scanner;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
    public class RecordService {
        @Autowired
        private RecordingRepository recordingRepository;
        @Value("${fileDir}")
        private String fileDir;
        @Autowired
        private S3Service s3Service;
        private static final int READ_LIMIT = 1024 * 1024 * 1024;
            public Recordings createRecording(MultipartFile file, String fileDir) throws Exception {
                // Generate a unique name for the recorded file
                Scanner scanner = new Scanner(System.in);



                // Save the file with the unique name

                AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
                DataLine.Info targetDataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

                if (!AudioSystem.isLineSupported(targetDataLineInfo)) {
                    throw new LineUnavailableException("Microphone is not supported.");
                }

                TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetDataLineInfo);
                targetDataLine.open(audioFormat);
                targetDataLine.start();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];

                long startTime = System.currentTimeMillis();

                System.out.println("Recording started.");

                while (System.currentTimeMillis() - startTime < 5000) { // Adjust the duration of recording as needed
                    int bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Recording finished.");

                targetDataLine.stop();
                targetDataLine.close();

                byte[] audioData = byteArrayOutputStream.toByteArray();


                //Create Directory
                File directory = new File(fileDir);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs();
                    if (!created) {
                        throw new IllegalStateException("Failed to create the directory: " + fileDir);
                    }
                }
                String name = file.getOriginalFilename();
                String recordUrl = s3Service.uploadFile(file);
                Recordings recordings = new Recordings();
                recordings.setRecordUrl(recordUrl);
                recordings.setName(name);
                recordings.setFormat(audioFormat.toString());
                recordings.setDuration(5000000L);
                recordings.setBytes(audioData);



                recordingRepository.save(recordings);
                return recordings;


            }



        public Optional<Recordings> getRecordingById(String id) {
            return recordingRepository.findById(id);
        }

        public void deleteRecordingById( String id) {
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























