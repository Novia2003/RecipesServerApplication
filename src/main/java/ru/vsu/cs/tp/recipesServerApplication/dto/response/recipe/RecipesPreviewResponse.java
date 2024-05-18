package ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecipesPreviewResponse {
    private List<RecipePreviewResponse> results;
}
