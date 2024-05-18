package ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipesPreview {
    private List<RecipePreview> results;
}
