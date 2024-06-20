package ru.vsu.cs.tp.recipesServerApplication.configuration.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "spoonacular")
public class SpoonacularProperties {
    private String url;
    private String apiKey;
}