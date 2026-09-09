package com.oconde.aws3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import com.oconde.aws3.service.S3Service;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            String key = file.getOriginalFilename();
            s3Service.uploadFile(key, file.getBytes());
            return "File uploaded successfully: " + key;
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }

    @GetMapping("/download/{key}")
    public byte[] downloadFile(@PathVariable("key") String key) {
        try {
            return s3Service.downloadFile(key);
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file: " + e.getMessage());
        }
    }


}
