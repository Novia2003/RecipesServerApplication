package ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe;

import lombok.Data;

@Data
public class RecipePreviewResponse {
    private Long id;
    private String title;
    private String image;
    private Boolean isUserRecipe;
    private Boolean isFavouriteRecipe;
}
