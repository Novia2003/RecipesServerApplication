package ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe;

import lombok.Data;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.IngredientDTOResponse;

import java.util.List;

@Data
public class RecipeAllInfoResponse {
    private Long id;
    private String title;
    private String image;
    private String description;
    private int readyInMinutes;
    private List<IngredientDTOResponse> extendedIngredients;
    private List<String> steps;
    private Boolean isUserRecipe;
    private Boolean isFavouriteRecipe;
}

