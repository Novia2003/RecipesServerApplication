package ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipePreview {
    private Long id;
    private String title;
    private String image;
}
