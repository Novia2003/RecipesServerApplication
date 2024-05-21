//package ru.vsu.cs.tp.recipesServerApplication.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.vsu.cs.tp.recipesServerApplication.configuration.rest.S3Properties;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectResponse;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//@Service
//@RequiredArgsConstructor
//public class S3Service {
//
//    private final S3Client s3Client;
//
//    private final S3Properties s3Properties;
//
//    public String uploadPhoto(byte[] photo, String photoType, String key) {
//        try (InputStream inputStream = new ByteArrayInputStream(photo)) {
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(s3Properties.getBucketName())
//                    .key(key)
//                    .contentType(photoType)
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, photo.length));
//            return getPhotoUrl(key);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to upload photo to S3", e);
//        }
//    }
//
//    private String getPhotoUrl(String key) {
//        return "https://" + s3Properties.getBucketName() + ".s3." + s3Client.toString() + ".amazonaws.com/" + key;
//    }
//}
