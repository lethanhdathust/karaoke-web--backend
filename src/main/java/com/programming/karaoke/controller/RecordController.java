package com.programming.karaoke.controller;

import com.programming.karaoke.model.Recordings;
import com.programming.karaoke.repository.RecordingRepository;
import com.programming.karaoke.service.AudioService;
import com.programming.karaoke.service.RecordService;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Recording;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
@RequestMapping("/recordings")
@Slf4j
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private RecordingRepository recordingRepository;

    @Autowired
    private AudioService audioService;


    @GetMapping("/testData")
    public String testData(HttpServletRequest request){
        log.info("[testData] da truy cap thanh cong {}", request.getHeader("thuoctinha"));

        return "";
    }


@PostMapping("/createRecording")
public ResponseEntity<String> createRecording(@RequestParam("file") MultipartFile file,
                                              @RequestParam("fileDir") String fileDir,
                                              @RequestParam("customName") String customName) {
    try {
        Recordings createdRecording = recordService.createRecording(file, fileDir,customName);
        return ResponseEntity.ok("Recording created successfully. ID: " + createdRecording.getId());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create recording: " + e.getMessage());
    }
}

    @GetMapping("/{id}/play")
    public ResponseEntity<byte[]> playRecording(@PathVariable("id") String id) {
        Optional<Recordings> optionalRecordings = recordService.getRecordingById(String.valueOf(id));
        if (optionalRecordings.isPresent()) {
            Recordings recordings = optionalRecordings.get();
            try {
                byte[] bytes = recordService.getRecordingBytes(recordings);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(recordings.getName()).build());
                return  new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        else {
            return ResponseEntity.notFound().build();
          }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deleteRecording(@PathVariable("id") String id) {
        try {
            recordService.deleteRecordingById(String.valueOf(id));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //
    //Beautifying voice
    //
    @PostMapping("/{id}/beautify")
    public ResponseEntity<?> beautifyRecording(@PathVariable("id") String id) {
        Optional<Recordings> optionalRecordings = recordService.getRecordingById(String.valueOf(id));
        if (optionalRecordings.isPresent()) {
            Recordings recordings = optionalRecordings.get();
            try {
                // Save the recording bytes to a temporary file
                Path tempFile = Files.createTempFile("temp", ".wav");
                try (InputStream inputStream = new ByteArrayInputStream(recordings.getBytes())) {
                    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }

                // Execute the Python script for voice beautification
                String pythonScript = "AutotunePython/autotune.py"; // Update with the actual path to your modified Python script
                String inputFile = tempFile.toString();
                String outputFile = tempFile.getParent().resolve(tempFile.getFileName().toString().replace(".wav", "_pitch_corrected.wav")).toString();
                String[] command = {"python", pythonScript, tempFile.toString()};
                Process process = Runtime.getRuntime().exec(command);

                // Wait for the Python script execution to complete
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    // Read the beautified recording from the output file
                    byte[] beautifiedBytes = Files.readAllBytes(Paths.get(outputFile));

                    // Update the beautified recording in the database or return it as a response
                    // update the recording in the database
                    recordings.setBytes(beautifiedBytes);
                    recordingRepository.save(recordings);

                    // Delete the temporary input and output files
                    Files.deleteIfExists(tempFile);
                    Files.deleteIfExists(Paths.get(outputFile));

                    return ResponseEntity.ok().build();
                } else {
                    // Handle the case when the Python script execution fails
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


//    @PostMapping("/createRecording")
//    public ResponseEntity<?> createRecording(@RequestParam("file")MultipartFile file, @Value("${fileDir}") String fileDir) {
//        log.info("[createRecording] fileDir {}", fileDir);
//        try {
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getInputStream());
//            AudioFormat format = audioInputStream.getFormat();
//            long duration = (long) (audioInputStream.getFrameLength() / format.getFrameRate() * 1000);
//            String name = file.getOriginalFilename();
//            String filePath = fileDir + "/" + name;
//            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//            Recordings recordings = new Recordings();
//            recordings.setName(name);
//            recordings.setFormat(String.valueOf(format));
//            recordings.setFilePath(filePath);
//            recordings.setDuration(duration);
//
//            recordingRepository.save(recordings);
//
//            return ResponseEntity.ok(). build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//
//        }
//    }
//
//    @GetMapping("/{id}/play")
//    public ResponseEntity<byte[]> playRecording(@PathVariable("id") Long id) {
//        Optional<Recordings> optionalRecordings = recordingRepository.findById(id);
//        if (optionalRecordings.isPresent()) {
//            Recordings recordings = optionalRecordings.get();
//            try {
//                Path filePath = Paths.get(getClass().getResource("/recordings" + recordings.getName()).toURI());
//                byte[] bytes = Files.readAllBytes(filePath);
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(recordings.getName()).build());
//                return  new ResponseEntity<>(bytes, headers, HttpStatus.OK);
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        }
//        else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @DeleteMapping("{id}/delete")
//    public ResponseEntity<?> deleteRecording(@PathVariable("id") Long id) {
//        try {
//            recordingRepository.deleteById(id);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }