package com.oconde.aws3.service;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    @Autowired
    S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public void uploadFile(String key, byte[] content) {
        try {
            s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(),
                    RequestBody.fromBytes(content));
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    public byte[] downloadFile(String key) {
        try {
            return s3Client.getObjectAsBytes(builder -> builder.bucket(bucketName).key(key)).asByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from S3", e);
        }
    }
}
