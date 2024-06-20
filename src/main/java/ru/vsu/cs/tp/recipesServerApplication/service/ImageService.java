package ru.vsu.cs.tp.recipesServerApplication.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.configuration.rest.MinioProperties;
import ru.vsu.cs.tp.recipesServerApplication.exception.ImageUploadException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public String upload(byte[] imageBytes, String originalFilename) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: " + e.getMessage());
        }

        if (imageBytes == null || imageBytes.length == 0 || originalFilename == null) {
            throw new ImageUploadException("Image must have name and content.");
        }

        String fileName = generateFileName(originalFilename);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        saveImage(inputStream, fileName);
        return minioProperties.getUrl() + "/" + minioProperties.getBucket() + "/" + fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(String originalFilename) {
        String extension = getExtension(originalFilename);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(final InputStream inputStream, final String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }
}
