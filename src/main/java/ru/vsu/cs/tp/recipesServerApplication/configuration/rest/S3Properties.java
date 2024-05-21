package ru.vsu.cs.tp.recipesServerApplication.configuration.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Data
//@ConfigurationProperties(prefix = "s3")
public class S3Properties {
    private String bucketName;
    private String accessKeyId;
    private String secretAccessKey;
    private String region;
}
