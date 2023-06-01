package com.programming.karaoke.service;

import com.programming.karaoke.model.Recordings;
import com.programming.karaoke.repository.RecordingRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    private RecordingRepository recordingRepository;
    @Value("${fileDir}")
    private String fileDir;

    public Recordings createRecording(MultipartFile file) throws Exception {
            InputStream bufferedStream = new BufferedInputStream(file.getInputStream());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            AudioFormat format = audioInputStream.getFormat();
            long duration = (long) (audioInputStream.getFrameLength() / format.getFrameRate() * 1000);
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));

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
            return  recordings;


    }

    public Optional<Recordings> getRecordingById(Long id) {
        return recordingRepository.findById(id);
    }

    public void deleteRecordingById( Long id) {
            recordingRepository.deleteById(id);

    }

    public byte[] getRecordingBytes(Recordings recordings) throws  Exception {
        return  recordings.getBytes();
    }

//    public String getRecordingFileType(Recordings recordings) throws IOException, UnsupportedAudioFileException {
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(recordings.getBytes()));
//        AudioFormat format = audioInputStream.getFormat();
//        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(new ByteArrayInputStream(recordings.getBytes()));
//        String fileExtension = "";
//        if (fileFormat.getType() == AudioFileFormat.Type.WAVE) {
//            fileExtension = "wav";
//        } else if (fileFormat.getType() == AudioFileFormat.Type.AIFF) {
//            fileExtension = "aiff";
//        } else if (fileFormat.getType() == AudioFileFormat.Type.AU) {
//            fileExtension = "au";
//        } else if (fileFormat.getType() == AudioFileFormat.Type.SND) {
//            fileExtension = "snd";
//        }  else if (fileFormat.getType() == AudioFileFormat.Type.MPEG || fileFormat.getType() == AudioFileFormat.Type.MPEG_3) {
//            fileExtension = "mp3";
//        } else {
//            // unsupported file type
//            return "";
//        }
//        return fileExtension;
//    }

//    public String getRecordingFileType(AudioInputStream audioInputStream) throws UnsupportedAudioFileException, IOException {
//        AudioFormat format = audioInputStream.getFormat();
//        String fileExtension = "";
//        if (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
//            switch (format.getChannels()) {
//                case 1:
//                    fileExtension = "wav";
//                    break;
//                case 2:
//                    fileExtension = "wav";
//                    break;
//                default:
//                    // unsupported channel count
//                    return "";
//            }
//        } else if (format.getEncoding() == AudioFormat.Encoding.PCM_UNSIGNED) {
//            switch (format.getChannels()) {
//                case 1:
//                    fileExtension = "au";
//                    break;
//                case 2:
//                    fileExtension = "au";
//                    break;
//                default:
//                    // unsupported channel count
//                    return "";
//            }
//        } else if (format.getEncoding() == AudioFormat.Encoding.ALAW) {
//            fileExtension = "snd";
//        } else if (format.getEncoding() == AudioFormat.Encoding.ULAW) {
//            fileExtension = "snd";
//        } else if (format.getEncoding() == AudioFormat.Encoding.AIFF) {
//            fileExtension = "aiff";
//        } else if (format.getEncoding() == AudioFormat.Encoding.AU) {
//            fileExtension = "au";
//        } else if (format.getEncoding() == AudioFormat.Encoding.WAVE_PCM || format.getEncoding() == AudioFormat.Encoding.WAVE_FLOAT) {
//            fileExtension = "wav";
//        } else if (format.getEncoding() == AudioFormat.Encoding.VORBIS) {
//            fileExtension = "ogg";
//        } else if (format.getEncoding() == AudioFormat.Encoding.MP3) {
//            fileExtension = "mp3";
//        } else {
//            // unsupported audio format
//            return "";
//        }
//        return fileExtension;
//    }
}
















